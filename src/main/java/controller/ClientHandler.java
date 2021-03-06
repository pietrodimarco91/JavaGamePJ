package controller;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * This Class let every user to create a new match or to join in a pending match
 * that already exist.
 */
public class ClientHandler implements Runnable {

	private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

	private ArrayList<MatchHandler> matches;

	/**
	 * This attribute handles every interaction with the user.
	 */
	private ClientSideConnectorInt clientSideConnector;
	private ServerSideConnectorInt serverSideConnector;
	private String clientNickName;

	public ClientHandler(ClientSideConnectorInt clientSideConnector, ServerSideConnectorInt serverSideConnector,
			ArrayList<MatchHandler> matches, String clientNickName) {
		logger.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
		this.matches = matches;
		this.clientSideConnector = clientSideConnector;
		this.serverSideConnector = serverSideConnector;
		this.clientNickName=clientNickName;
	}

	/**
	 * This is the run() method of the Thread. It asks to the User if he wants
	 * to join in a pending match or to launch a new match.
	 */
	@Override
	public void run() {
		if (!joinMatch()) {
			launchNewMatch(new Date());
		}
	}

	/**
	 * This method let to launch a new match adding it to the matches.
	 * 
	 * @param date
	 */
	public synchronized void launchNewMatch(Date date) {
		int id = Server.getId();
		DateFormat dateFormat = new SimpleDateFormat();
		MatchHandler matchHandler = new MatchHandler(id, date, clientSideConnector, serverSideConnector,clientNickName);
		matchHandler.sendMessageToClient(
				"You launched a new match of Council Of Four on " + dateFormat.format(date) + " with ID " + id + "\nYou are the match creator and your ID is 0",
				0);
		try {
			serverSideConnector.setMatchHandler(matchHandler);
		} catch (RemoteException e) {
			logger.log(Level.INFO, "Remote Exception", e);
		}
		matches.add(matchHandler);
	}

	/**
	 * This method is used to let the user to join into a match. To do it
	 * control if there are pending && not full matches in this case it add the
	 * new player into the match. If there are no available matches it launch a
	 * new match.
	 */
	public synchronized boolean joinMatch() {
		Iterator<MatchHandler> iterator = matches.iterator();
		MatchHandler matchInList;
		boolean joined = false;
		while (iterator.hasNext() && !joined) {
			matchInList = iterator.next();
			if (matchInList.isPending() && !(matchInList.isFull())) {
				int myId = matchInList.getPlayers().size();
				matchInList.addPlayer(clientSideConnector, serverSideConnector, myId, clientNickName);
				matchInList.sendMessageToClient(
						"You joined an already existing match of Council Of Four with ID " + matchInList.getId() + "\nYour player ID is "+myId,
						myId);
				for (Player player : matchInList.getPlayers()) {
					if (player.getConnector() != clientSideConnector) {
						matchInList.sendMessageToClient("A new player joined this match! Currently there are "
								+ matchInList.getPlayers().size() + " players...", player.getId());
					}
				}
				try {
					serverSideConnector.setMatchHandler(matchInList);
				} catch (RemoteException e) {
					logger.log(Level.INFO, "Remote Exception", e);
				}
				joined = true;
			}
		}
		return joined;
	}

}

package controller;

import server.view.cli.ServerOutputPrinter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class initializes the game engine and the connection among the Clients.
 */
public class Server {
	/**
	 * The IP Address of the Server, that means the address that all Clients
	 * must know to connect to the game.
	 */
	private static final String ip = "localhost";

	/**
	 * The port of the specified IP Address (connection parameter).
	 */
	private static final int port = 2000;

	/**
	 * The boolean variable to stop the server
	 */
	private static boolean stopServer = false;

	/**
	 * Matches Ids
	 */
	private static int id = 0;
	SocketConnector socketConnector;
	/**
	 * These threads are used by Server to handle the different connections
	 * coming from the Clients
	 */
	private ExecutorService thread;
	/**
	 * The Server stores an array of currently on-going matches through their
	 * MatchHandlers
	 */
	private ArrayList<MatchHandler> matches;
	
	/**
	 * The ServerSocket used to accept socket connections from clients
	 */
	private ServerSocket welcomeSocket;

	/**
	 * A reference to the instance of the server (Singleton pattern)
	 */
	private static Server instance;

	private Server() {
		thread = Executors.newCachedThreadPool();
		this.matches = new ArrayList<MatchHandler>();
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			RMIConnectionInt b = new RMIConnection(matches, thread);
			Naming.bind("rmi://" + ip + "/registry", b);
			ServerOutputPrinter.printLine("[SERVER] Ready to receive RMI invocations.");
			ServerOutputPrinter.printLine("[SERVER] Ready to receive Socket Connections.");
			this.welcomeSocket = new ServerSocket(port);
			this.waitSocketConnection();
		} catch (AlreadyBoundException e) {
			ServerOutputPrinter.printLine(e.getMessage());
		} catch (MalformedURLException e) {
			ServerOutputPrinter.printLine(e.getMessage());
		} catch (RemoteException e) {
			ServerOutputPrinter.printLine(e.getMessage());
		} catch (IOException e) {
			ServerOutputPrinter.printLine(e.getMessage());
		}
	}

	/**
	 * Server implements Singleton pattern, that's why of this static method
	 * that ALWAYS returns the same instance of the Server. That's made to
	 * assure that this instance will be unique during the execution of the
	 * program.
	 * 
	 * @return the instance of the server
	 */
	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	public static int getId() {
		return id++;
	}

	/**
	 * This method is invoked when the server will have to shut down
	 */
	public static void stopServer() {
		stopServer = true;
	}

	/**
	 * This method waits the connection of the Clients and then the different
	 * Threads handle the different clients.
	 */
	private void waitSocketConnection() {
		while (!stopServer) {
			try {
				socketConnector = new SocketConnector(welcomeSocket.accept());
				socketConnector.start();
				socketConnector.sendToClient(
						new Packet("[SERVER] Socket Connection correctly established with Server engine!"));
				thread.submit(new ClientHandler(socketConnector, socketConnector, matches, "tempNickName"));
			} catch (IOException e) {
				ServerOutputPrinter.printLine(e.getMessage());
			}
		}
	}
}
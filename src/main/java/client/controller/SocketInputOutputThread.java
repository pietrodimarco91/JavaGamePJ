package client.controller;

import client.view.cli.ClientOutputPrinter;
import controller.ClientSideConnectorInt;
import controller.MatchHandler;
import controller.Packet;
import controller.ServerSideConnectorInt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;

/**
 * This class represents the client side connector when the client is connected
 * through Socket connection. This thread keeps running waiting for something
 * sent by the server, and it allows to send packets to the server as well. As
 * it implements two different kinds of interfaces, some methods will not be
 * implemented, as they aren't needed.
 */
public class SocketInputOutputThread extends Thread implements ClientSideConnectorInt, ServerSideConnectorInt {

	private ObjectInputStream inputObjectFromServer;
	private ObjectOutputStream outputObjectToServer;
	private boolean stop;
	private ClientGUIController guiController;

	public SocketInputOutputThread(Socket socket) {
		this.stop = false;
		try {
			outputObjectToServer = new ObjectOutputStream(socket.getOutputStream());
			inputObjectFromServer = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			ClientOutputPrinter.printLine(e.getMessage());
		}
	}

	// *****CLIENT SIDE METHOD******//

	@Override
	public void run() {
		while (!stop) {
			try {
				sendToClient((Packet) inputObjectFromServer.readObject());
			} catch (SocketException e) {
				ClientOutputPrinter.printLine("Critical error: Server went down and the connection has been closed.");
				break;
			} catch (IOException e) {
				ClientOutputPrinter.printLine(e.getMessage());
			} catch (ClassNotFoundException e) {
				ClientOutputPrinter.printLine(e.getMessage());
			}
		}
	}

	@Override
	public void sendToServer(Packet packet) throws RemoteException {
		try {
			outputObjectToServer.reset();
			outputObjectToServer.writeObject(packet);
			outputObjectToServer.flush();
		} catch (IOException e) {
			ClientOutputPrinter.printLine(e.getMessage());
		}
	}

	

	public void sendToClient(Packet packet) throws RemoteException {
		switch (packet.getHeader()) {
		case "MESSAGESTRING":
			ClientOutputPrinter.printLine(packet.getMessageString());
			if (guiController != null) {
				guiController.sendPacketToGUIController(packet);
			}
			break;
		case "UPDATE":
			if (guiController != null) {
				guiController.sendPacketToGUIController(packet);
			}
			break;
		case "CHAT":
			if (guiController != null) {
				guiController.sendPacketToGUIController(packet);
			}
			break;
		default:
		}
	}
	
	public void disconnect() {
		stop = true;
		try {
			outputObjectToServer.close();
			inputObjectFromServer.close();
		} catch (IOException e) {
			ClientOutputPrinter.printLine(e.getMessage());
		}
	}

	public void setGUIController(ClientGUIController controller) {
		this.guiController = controller;
	}

	// *****SERVER SIDE METHOD ******//

	@Override
	public void setMatchHandler(MatchHandler matchHandler) {

	}
	
	@Override
	public void setPlayerId(int id) {

	}
}

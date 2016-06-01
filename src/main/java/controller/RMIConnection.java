package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * This class is used in case of RMIConnection Connection, it handles the real interaction with the user.
 */
public class RMIConnection extends UnicastRemoteObject implements RMIConnectionInt {

    ArrayList<MatchHandler> matches;

    /**
     *These threads are used by Server to handle the different connections coming from the Clients
     */
    private ExecutorService thread;


    public RMIConnection(ArrayList<MatchHandler> matches, ExecutorService thread) throws RemoteException {
        this.matches=matches;
        this.thread=thread;

    }
    /**
     *The Client needs the serverSideRMIConnector because if not he wouldn't be able to consult the Server
     */
    @Override
    public ServerSideRMIConnectorInt connect(ClientSideRMIConnectorInt a) throws RemoteException {
        a.writeToClient("Connection RMI established");
        System.out.println(a.receiveIntFromClient());
        ServerSideRMIConnector serverSideRMIConnector=new ServerSideRMIConnector(a);
        thread.submit(new ClientHandler(serverSideRMIConnector,matches));
        return serverSideRMIConnector;
    }
}
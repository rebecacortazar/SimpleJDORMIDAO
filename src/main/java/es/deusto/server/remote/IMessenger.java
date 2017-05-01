package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.deusto.server.data.User;

public interface IMessenger extends Remote {
	
	String sayMessage(String login, String password, String message) throws RemoteException;
	void registerUser(String login, String password) throws RemoteException;
	User getUserMessages(String login) throws RemoteException;

}

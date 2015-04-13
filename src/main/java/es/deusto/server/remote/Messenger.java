package es.deusto.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.deusto.server.dao.IUserDAO;
import es.deusto.server.dao.UserDAO;
import es.deusto.server.data.Message;
import es.deusto.server.data.User;

public class Messenger extends UnicastRemoteObject implements IMessenger {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	IUserDAO dao;
	
	public Messenger() throws RemoteException {
		super();
		dao = new UserDAO();
	
	}
	public Messenger(IUserDAO udao) throws RemoteException {
		super();
		dao = udao;
	
	}
	
	public void registerUser(String login, String password) {
			
			System.out.println("Checking whether the user already exits or not: '" + login +"'");
			User user = null;
			try {
				user = dao.retrieveUser(login);
			} catch (Exception  e) {
				System.out.println("Exception launched: " + e.getMessage());
			}
			
			if (user != null) {
				System.out.println("The user exists. So, setting new password for User: " + login);
				user.setPassword(password);
				System.out.println("Password set for User: " + login);
				dao.updateUser(user);
			} else {
				System.out.println("Creating user: " + login);
				user = new User(login, password);
				dao.storeUser(user);				 
				System.out.println("User created: " + login);
			}
	}
	

	public String sayMessage(String login, String password, String message) throws RemoteException {

			System.out.println("Retrieving the user: '" + login +"'");
			User user = null;
			try {
				user = dao.retrieveUser(login);
			} catch (Exception  e) {
				System.out.println("Exception launched: " + e.getMessage());
			}
			
			System.out.println("User retrieved: " + user);
			if (user != null)  {
				Message message1 = new Message(message);
				message1.setUser(user);
				user.getMessages().add(message1);
				dao.updateUser(user);	
				cont++;
				System.out.println(" * Client number: " + cont);
				return message;
			}
			else {
				System.out.println("Login details supplied for message delivery are not correct");
				throw new RemoteException("Login details supplied for message delivery are not correct");
			} 
		}
	
	public User getUserMessages(String login) throws RemoteException {
		
		System.out.println("Checking whether the user already exits or not: '" + login +"'");
		User user = null;
		try {
			user = dao.retrieveUser(login);
		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
		}
		
		if (user != null) {
			System.out.println("Returning the User and its messages to the RMI Client: " + login);
			return user;
		} else {
			System.out.println("The user does not exist, no possibility of retrieving messages ...: " + login);
			throw new RemoteException("Login details supplied for message retrieval are not correct");
		}
	}
}

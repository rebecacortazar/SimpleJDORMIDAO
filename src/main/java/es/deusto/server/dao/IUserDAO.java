package es.deusto.server.dao;

import es.deusto.server.data.User;

public interface IUserDAO {
	public void storeUser (User u);
	public User retrieveUser (String login);
	public void updateUser (User u);

}

package es.deusto.server.dao;

import es.deusto.server.data.User;

public interface IUserDAO {
	void storeUser(User u);
	User retrieveUser(String login);
	void updateUser(User u);

}

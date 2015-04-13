package es.deusto.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;  

import java.rmi.RemoteException;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;  
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;  
import org.mockito.runners.MockitoJUnitRunner; 
import org.junit.Before;
import org.junit.Test;

import es.deusto.server.dao.IUserDAO;
import es.deusto.server.data.Message;
import es.deusto.server.data.User;
import es.deusto.server.remote.Messenger;

/**
 * 
 * @author cortazar
 * Testing of the Service Layer, mocking the DAO layer
 */
@RunWith(MockitoJUnitRunner.class)  
public class DAOMockTest {
	
	@Mock
	IUserDAO dao;
	
	Messenger m;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(DAOMockTest.class);
	}

	@Before
	public void setUp() throws Exception {		
		m = new Messenger(dao);

	}

	@Test
	public void testRegisterUserCorrectly() {
		User u = new User("cortazar","cortazar");
		// Stubbing - return a given value when a specific method is called
		when( dao.retrieveUser("cortazar") ).thenReturn( null );
		m.registerUser("cortazar", "cortazar");
		when( dao.retrieveUser("cortazar") ).thenReturn( u);
		
		//Use ArgumentCaptor to capture argument values for further assertions.
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );
		
		// Setting expectations -  the method storeUser() is called once and the argument is intercepted
		verify (dao).storeUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		
		assertEquals( "cortazar", newUser.getLogin());
		
	}
	
	@Test
	public void testRegisterUserAlreadyExists() {
		User u = new User("cortazar","cortazar");
		
		when( dao.retrieveUser("cortazar") ).thenReturn(u);
		m.registerUser("cortazar", "dipina");
		
		
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );
		verify (dao).updateUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		assertEquals( "dipina", newUser.getPassword());
		
	}

	@Test(expected=RemoteException.class)
	public void testSayMessageUserInvalid() throws RemoteException {
		
		when( dao.retrieveUser("cortazar") ).thenReturn( null );
		
		m.sayMessage("cortazar", "cortazar", "testing message");

		verify (dao).retrieveUser("cortazar");
			
	}
	
	@Test
	public void testSayMessageUserValid() throws RemoteException {
		// Setting up the test data
		User u = new User("cortazar","cortazar");
		Message mes = new Message("testing message");
		mes.setUser(u);
		u.addMessage(mes) ;
		
		//Stubbing
		when( dao.retrieveUser("cortazar") ).thenReturn(u);
		
		//Calling the method under test
		
		m.sayMessage("cortazar", "cortazar", "testing message");
		
		// Verifying the outcome
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );
		verify (dao).updateUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		
		assertEquals( "cortazar", newUser.getMessages().get(0).getUser().getLogin());
		
	}

}

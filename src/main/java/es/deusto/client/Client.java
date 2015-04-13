package es.deusto.client;

import java.rmi.RMISecurityManager;

import es.deusto.server.data.Message;
import es.deusto.server.data.User;
import es.deusto.server.remote.IMessenger;

@SuppressWarnings("deprecation")
public class Client {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			IMessenger objHello = (IMessenger) java.rmi.Naming.lookup(name);
			// Register to be allowed to send messages
			System.out.println("Register a user for the first time: dipina");
			objHello.registerUser("dipina", "dipina");
			System.out.println("Change the password as the user is already registered: cortazar");
			objHello.registerUser("dipina", "cortazar");
			System.out.println("* Message coming from the server: '" + objHello.sayMessage("dipina", "cortazar", "This is test 1!") + "'");
			System.out.println("* Message coming from the server: '" + objHello.sayMessage("dipina", "cortazar", "This is test 2!") + "'");
			User u = objHello.getUserMessages("dipina");
			for (Message m: u.getMessages()) {
				
				System.out.println(m);
				
			}
			
		} catch (Exception e) {
			System.err.println("RMI Example exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
package zc.studdy.rpc.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import zc.studdy.rpc.rmi.server.clock.ClockClientRegistry;
import zc.studdy.rpc.rmi.server.clock.impl.ClockClientRegistryImpl;
import zc.studdy.rpc.rmi.server.hello.impl.HelloServiceImpl;
import zc.studdy.rpc.rmi.shared.HelloService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


/**
 * The main class that instanciate the service classes, bind them into the RMI registry
 * and start the Server.
 *
 * @author Pascal
 */
public class ServerMain {

	public static void main(String args[]) throws RemoteException, MalformedURLException {
		int servicePort = extractServicePort(args);
		ServiceLocator locator = new ServiceLocator("localhost", servicePort);

		startRegistry(servicePort);

		// create the HelloServiceFacade and bind it into the RMI registry
		Naming.rebind(locator.getHelloServiceUrl(), createHelloServiceFacade());
		System.out.println("HelloService ready on: " + locator.getHelloServiceUrl());

		// create the ClockServiceFacade and bind it into the RMI registry
		Naming.rebind(locator.getClockServiceUrl(), createClockServiceFacade());
		System.out.println("ClockService ready on: " + locator.getClockServiceUrl());

//		// start the asynchronous clock.
//		new ClockServiceImpl(callbackRegistry);
	}

	private static HelloServiceFacade createHelloServiceFacade() throws RemoteException {
		HelloService helloService = new HelloServiceImpl();
		HelloServiceFacade helloServiceFacade = new HelloServiceFacade(helloService);

		return helloServiceFacade;
	}

	private static ClockServiceFacade createClockServiceFacade() throws RemoteException {
		ClockClientRegistry callbackRegistry = new ClockClientRegistryImpl();
		ClockServiceFacade clockServiceFacade = new ClockServiceFacade(callbackRegistry);

		return clockServiceFacade;
	}

	/**
	 * This method starts a RMI registry on the local host, if it does not already exists
	 * at the specified port number.
	 *
	 * @param port
	 * @throws RemoteException
	 */
	private static void startRegistry(int port) throws RemoteException {
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			// This call will throw an exception
			// if the registry does not already exist
			registry.list();
		}
		catch (RemoteException e) {
			// No valid registry at that port.
			LocateRegistry.createRegistry(port);
		}
	}

	private static int extractServicePort(String args[]) {
		if (args.length >= 1) {
			try {
				return Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
			}
		}

		return 1099;
	}
}

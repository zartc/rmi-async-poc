package zc.studdy.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import zc.studdy.rmi.server.asynchronous.HelloCallbackRegistry;
import zc.studdy.rmi.server.asynchronous.impl.AsynchronousHelloServiceImpl;
import zc.studdy.rmi.server.asynchronous.impl.HelloCallbackRegistryImpl;
import zc.studdy.rmi.server.synchronous.SynchronousHelloService;
import zc.studdy.rmi.server.synchronous.impl.SynchronousHelloServiceImpl;
import zc.studdy.rmi.shared.ServiceLocator;


/**
 * The main programe that launch the HelloServer.
 *
 * @author Pascal
 */
public class ServerMain {

	public static void main(String args[]) throws RemoteException, MalformedURLException {
		int servicePort = extractServicePort(args);
		ServiceLocator locator = new ServiceLocator("localhost", servicePort);

		startRegistry(servicePort);
		Naming.rebind(locator.getHelloServiceUrl(), createHelloServerFacade());
		System.out.println("HelloService ready on: " + locator.getHelloServiceUrl());
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

	private static HelloServerFacade createHelloServerFacade() throws RemoteException {
		SynchronousHelloService synchronousHelloService = new SynchronousHelloServiceImpl();
		HelloCallbackRegistry callbackRegistry = new HelloCallbackRegistryImpl();
		HelloServerFacade helloServerFacade = new HelloServerFacade(synchronousHelloService, callbackRegistry);

		// start the asynchronous service.
		new AsynchronousHelloServiceImpl(callbackRegistry);

		return helloServerFacade;
	}
}

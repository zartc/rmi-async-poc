package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;

import zc.studdy.rpc.rmi.server.clock.impl.ClockServiceImpl;
import zc.studdy.rpc.rmi.server.clock.impl.ClockTask;
import zc.studdy.rpc.rmi.server.clock.impl.ClockTask.Observer;
import zc.studdy.rpc.rmi.server.hello.impl.GreetingServiceImpl;
import zc.studdy.rpc.rmi.shared.GreetingService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


/**
 * The main class that instanciate the service classes, bind them into the RMI registry
 * and start the Server.
 *
 * @author Pascal
 */
public class Server {
	private int port;
	private ServiceLocator locator;

	public Server(int port, ServiceLocator locator) {
		this.port = port;
		this.locator = locator;
	}

	public void start() throws RemoteException {
		startRegistry(port);

		// create the HelloServiceFacade and bind it into the RMI registry
		locator.registerGreetingService(createHelloServiceFacade());

		// create the ClockServiceFacade and bind it into the RMI registry
		ClockServiceFacade clockService = createClockServiceFacade();
		locator.registerClockService(clockService);

		// start the asynchronous clock.
		new ClockTask((Observer)clockService.unwrap(), 5, 5, TimeUnit.SECONDS);
	}

	private GreetingServiceFacade createHelloServiceFacade() throws RemoteException {
		GreetingService greetingService = new GreetingServiceImpl();
		GreetingServiceFacade greetingServiceFacade = new GreetingServiceFacade(greetingService);

		return greetingServiceFacade;
	}

	private ClockServiceFacade createClockServiceFacade() throws RemoteException {
		ClockServiceImpl clockService = new ClockServiceImpl();
		ClockServiceFacade clockServiceFacade = new ClockServiceFacade(clockService);

		return clockServiceFacade;
	}

	/**
	 * This method starts a RMI registry on the local host, if it does not already exists
	 * at the specified port number.
	 *
	 * @param port
	 * @throws RemoteException
	 */
	private void startRegistry(int port) throws RemoteException {
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


	// ----- MAIN -----

	public static void main(String args[]) throws RemoteException {
		int servicePort = extractServicePort(args);
		ServiceLocator locator = new ServiceLocator("localhost", servicePort);

		new Server(servicePort, locator).start();
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

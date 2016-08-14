package zc.studdy.rpc.rmi.test;

import java.rmi.RemoteException;

import zc.studdy.rpc.rmi.client.ClockServiceBusinessDelegate;
import zc.studdy.rpc.rmi.client.GreetingBusinessDelegate;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


public class Client {
	private GreetingBusinessDelegate greetingBusinessDelegate;
	private ClockServiceBusinessDelegate clockServiceBusinessDelegate;

	public Client(GreetingBusinessDelegate greetingBusinessDelegate, ClockServiceBusinessDelegate clockServiceBusinessDelegate) {
		this.greetingBusinessDelegate = greetingBusinessDelegate;
		this.clockServiceBusinessDelegate = clockServiceBusinessDelegate;
	}

	public void start() throws RemoteException {
		// make a synchronous call
		System.out.println("Server found, it says: " + greetingBusinessDelegate.getGreetingMessage("Pascal"));

		// then create a Callback to pass to the server for it to call us back asynchronously
		MyCallback callback = new MyCallback();
		clockServiceBusinessDelegate.subscribe(callback);
		System.out.println("Just registered the callback.");

		try {
			Thread.sleep(30 * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		clockServiceBusinessDelegate.unsubscribe(callback);
		System.out.println("Just unregistered the callback.");
	}


	// ----- MAIN -----

	public static void main(String args[]) throws RemoteException {
		int port = parseServicePort(args.length > 0 ? args[0] : null);

		ServiceLocator serviceLocator = new ServiceLocator("localhost", port);
		GreetingBusinessDelegate greetingBusinessDelegate = new GreetingBusinessDelegate(serviceLocator);
		ClockServiceBusinessDelegate clockServiceBusinessDelegate = new ClockServiceBusinessDelegate(serviceLocator);

		new Client(greetingBusinessDelegate, clockServiceBusinessDelegate).start();

		System.exit(0);
	}

	private static int parseServicePort(String args) {
		try {
			return Integer.parseInt(args);
		}
		catch (NumberFormatException e) {
			return 1099;
		}
	}
}

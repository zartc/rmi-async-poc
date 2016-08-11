package zc.studdy.rpc.rmi.test;

import java.rmi.RemoteException;

import zc.studdy.rpc.rmi.shared.ClockService;
import zc.studdy.rpc.rmi.shared.GreetingService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


public class ClientMain {

	public static void main(String args[]) throws RemoteException {
		int servicePort = parseServicePort(args.length > 0 ? args[0] : "");

		// create the locator
		ServiceLocator locator = new ServiceLocator("localhost", servicePort);

		// and use it to locate the Hello and the Clock services
		GreetingService greetingService = locator.locateHelloService();

		// make a synchronous call
		System.out.println("Server found, it says: " + greetingService.computeGreetingMessage("Pascal"));

		// then create a Callback to pass to the server for it to call us back asynchronously
		ClockService.Callback callback = new MyCallback();
		ClockService clockService = locator.locateClockService();
		clockService.subscribe(callback);

		System.out.println("Just registered the callback.");

		try {
			Thread.sleep(30 * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		clockService.unsubscribe(callback);
		System.out.println("Just unregistered the callback.");

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

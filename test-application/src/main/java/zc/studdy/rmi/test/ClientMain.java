package zc.studdy.rmi.test;

import java.rmi.RemoteException;

import zc.studdy.rmi.shared.HelloService;
import zc.studdy.rmi.shared.HelloServiceCallback;
import zc.studdy.rmi.shared.ServiceLocator;


public class ClientMain {

	public static void main(String args[]) throws RemoteException {
		int servicePort = parseServicePort(args.length > 0 ? args[0] : "");

		// create the locator
		ServiceLocator locator = new ServiceLocator("localhost", servicePort);

		// and use it to locate the Hello and the Clock services
		HelloService helloService = locator.locateHelloService();

		// make a synchronous call
		System.out.println("Server found, it says: " + helloService.sayHello());

		// then create a Callback to pass to the server for it to call us back asynchronously
		HelloServiceCallback callback = new MyCallback();
		helloService.registerCallback(callback);

		System.out.println("Just registered the callback.");

		try {
			Thread.sleep(30 * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		helloService.unregisterCallback(callback);
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

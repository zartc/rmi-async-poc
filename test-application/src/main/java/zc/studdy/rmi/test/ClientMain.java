package zc.studdy.rmi.test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import zc.studdy.rmi.client.HelloServiceDelegate;
import zc.studdy.rmi.shared.HelloServiceCallback;


public class ClientMain {

	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException {
		HelloServiceDelegate delegate = new HelloServiceDelegate(args.length > 0 ? args[0] : null);

		System.out.println("Server found, it says: " + delegate.sayHello());

		HelloServiceCallback callback = new MyCallback();
		delegate.registerCallback(callback);
		System.out.println("Registered callback.");

		try {
			Thread.sleep(30 * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		delegate.unregisterCallback(callback);
		System.out.println("Unregistered callback.");

		System.exit(0);
	}
}

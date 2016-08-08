package zc.studdy.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import zc.studdy.rmi.shared.HelloService;
import zc.studdy.rmi.shared.HelloServiceCallback;


public class HelloServiceDelegate implements HelloService {

	private HelloService helloService;

	public HelloServiceDelegate(String s) throws MalformedURLException, RemoteException, NotBoundException {
		String helloServiceUrl = "rmi://localhost:" + parseServicePort(s) + "/callback";

		// find the remote service
		this.helloService = (HelloService)Naming.lookup(helloServiceUrl);
	}

	@Override
	public String sayHello() throws RemoteException {
		return helloService.sayHello();
	}

	@Override
	public void registerCallback(HelloServiceCallback callback) throws RemoteException {
		helloService.registerCallback(callback);
	}

	@Override
	public void unregisterCallback(HelloServiceCallback callback) throws RemoteException {
		helloService.unregisterCallback(callback);
	}

	private int parseServicePort(String args) {
		if (args != null) {
			try {
				return Integer.parseInt(args);
			}
			catch (NumberFormatException e) {
			}
		}

		return 1099;
	}

}

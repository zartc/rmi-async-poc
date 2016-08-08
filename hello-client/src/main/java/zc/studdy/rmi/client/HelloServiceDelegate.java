package zc.studdy.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import zc.studdy.rmi.shared.HelloService;
import zc.studdy.rmi.shared.HelloServiceCallback;


/**
 * The ServiceDelegate hides the complexity of locating and calling the remote service.
 *
 * @author Pascal
 */
public class HelloServiceDelegate implements HelloService {

	private HelloService helloService;

	public HelloServiceDelegate(int registryPort) throws RuntimeException {
		String helloServiceUrl = "rmi://localhost:" + String.valueOf(registryPort) + "/callback";

		try {
			// find the remote service
			this.helloService = (HelloService)Naming.lookup(helloServiceUrl);
		}
		catch (MalformedURLException | NotBoundException e) {
			throw new RuntimeException("not found at the given url: " + helloServiceUrl, e);
		}
		catch (RemoteException e) {
			throw new RuntimeException("not found at the given url: " + helloServiceUrl, e.getCause());
		}
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


}

package zc.studdy.rmi.client;

import java.rmi.RemoteException;

import zc.studdy.rmi.shared.HelloService;
import zc.studdy.rmi.shared.HelloServiceCallback;
import zc.studdy.rmi.shared.ServiceLocator;


/**
 * The ServiceDelegate hides the complexity of locating and calling the remote service.
 *
 * @author Pascal
 */
@Deprecated
public class HelloServiceDelegate implements HelloService {

	private HelloService helloService;

	public HelloServiceDelegate(ServiceLocator locator) throws RuntimeException {
		this.helloService = locator.locateHelloService();
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

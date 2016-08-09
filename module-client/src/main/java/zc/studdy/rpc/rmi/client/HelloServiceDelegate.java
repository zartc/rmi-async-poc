package zc.studdy.rpc.rmi.client;

import java.rmi.RemoteException;

import zc.studdy.rpc.rmi.shared.HelloService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


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
}

package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rpc.rmi.shared.HelloService;


/**
 * The Facade expose the HelloService to the client through the choosen remote access
 * technologie (RMI in this case, but other implementations could be developped : REST,
 * SOAP, JMS, Akka, etc).
 *
 * @author Pascal
 */
public class HelloServiceFacade extends UnicastRemoteObject implements HelloService {
	private static final long serialVersionUID = 1L;

	private HelloService helloService;

	protected HelloServiceFacade(HelloService helloService) throws RemoteException {
		super();
		this.helloService = helloService;
	}

	@Override
	public String sayHello() throws RemoteException {
		return helloService.sayHello();
	}
}

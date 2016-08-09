package zc.studdy.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rmi.server.asynchronous.HelloCallbackRegistry;
import zc.studdy.rmi.server.synchronous.SynchronousHelloService;
import zc.studdy.rmi.shared.HelloService;
import zc.studdy.rmi.shared.HelloServiceCallback;


/**
 * The Facade expose the HelloService to the client through the choosen remote access
 * technologie (RMI in this case, but other implementations could be developped : REST,
 * SOAP, JMS, Akka, etc).
 *
 * @author Pascal
 */
public class HelloServerFacade extends UnicastRemoteObject implements HelloService {
	private static final long serialVersionUID = 1L;

	private SynchronousHelloService synchronousHelloService;
	private HelloCallbackRegistry callbackRegistry;

	protected HelloServerFacade(SynchronousHelloService synchronousHelloService,
			HelloCallbackRegistry callbackRegistry) throws RemoteException {
		this.synchronousHelloService = synchronousHelloService;
		this.callbackRegistry = callbackRegistry;
	}

	@Override
	public String sayHello() throws RemoteException {
		return synchronousHelloService.sayHello();
	}

	@Override
	public void registerCallback(HelloServiceCallback callback) throws RemoteException {
		callbackRegistry.registerCallback(callback);
	}

	@Override
	public void unregisterCallback(HelloServiceCallback callback) throws RemoteException {
		callbackRegistry.unregisterCallback(callback);
	}
}

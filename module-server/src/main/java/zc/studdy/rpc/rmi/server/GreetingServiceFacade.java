package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rpc.rmi.shared.GreetingService;


/**
 * The Facade expose the Service to the client through the choosen remote access
 * technologie (RMI in this case, but other implementations could be developped : REST,
 * SOAP, JMS, Akka, etc).
 *
 * @author Pascal
 */
public class GreetingServiceFacade extends UnicastRemoteObject implements GreetingService {
	private static final long serialVersionUID = 1L;

	private GreetingService greetingService;

	protected GreetingServiceFacade(GreetingService greetingService) throws RemoteException {
		super();
		this.greetingService = greetingService;
	}

	@Override
	public String computeGreetingMessage(String name) throws RemoteException {
		return greetingService.computeGreetingMessage(name);
	}

	public GreetingService unwrap() {
		return greetingService;
	}
}

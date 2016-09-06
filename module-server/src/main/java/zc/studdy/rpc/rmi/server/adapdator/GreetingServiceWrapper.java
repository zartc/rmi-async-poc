package zc.studdy.rpc.rmi.server.adapdator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CompletableFuture;

import zc.studdy.rpc.rmi.shared.GreetingService;


public class GreetingServiceWrapper extends UnicastRemoteObject implements GreetingService {
	private static final long serialVersionUID = 1L;

	/**
	 * The MessageGenerator used to compute the greeting message.
	 */
	private final GreetingService greetingService;

	public GreetingServiceWrapper(GreetingService greetingService) throws RemoteException {
		this.greetingService = greetingService;
	}

	@Override
	public String computeGreetingMessage(String personName) throws RemoteException {
		return greetingService.computeGreetingMessage(personName);
	}

	@Override
	public CompletableFuture<String> computeGreetingMessageAsync(String personName) throws RemoteException {
		return greetingService.computeGreetingMessageAsync(personName);
	}

}

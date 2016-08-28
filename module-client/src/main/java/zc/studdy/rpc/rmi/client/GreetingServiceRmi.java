package zc.studdy.rpc.rmi.client;

import java.rmi.RemoteException;
import java.util.concurrent.CompletableFuture;

import zc.studdy.rpc.rmi.shared.GreetingService;


/**
 * A proxy that calls the remote GreetingService via RMI and translate RemoteException to
 * RuntimeException.
 *
 * @author Pascal
 */
public class GreetingServiceRmi implements GreetingService {

	private final GreetingService target;

	/**
	 * Constructor.
	 *
	 * @param target
	 */
	public GreetingServiceRmi(GreetingService target) {
		this.target = target;
	}

	@Override
	public String computeGreetingMessage(String personName) {
		try {
			return target.computeGreetingMessage(personName);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service unavailable", e);
		}
	}

	@Override
	public CompletableFuture<String> computeGreetingMessageAsync(String personName) throws RemoteException {
		try {
			return target.computeGreetingMessageAsync(personName);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service unavailable", e);
		}
	}
}

package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CompletableFuture;

import zc.studdy.rpc.rmi.server.internal.MessageGenerator;
import zc.studdy.rpc.rmi.shared.GreetingService;


/**
 * The implementation of the GreetingService interface. The GreetingService is marqued as
 * Remote thus this implementation must extends UnicastRemoteObject to provide remoting
 * via RMI.
 * <p>
 * The service is so simple that we do not use a wrapper to isolate the RMI plumbing.
 * Still the service use a helper class to generate the correct message.
 *
 * @author Pascal
 */
public class GreetingServiceImpl extends UnicastRemoteObject implements GreetingService {
	private static final long serialVersionUID = 1L;

	/**
	 * The MessageGenerator used to compute the greeting message.
	 */
	private final MessageGenerator messageGenerator;


	/**
	 * Constructor.
	 *
	 * @param messageGenerator the messageGenerator that this GreetingService must use to
	 *        compute the greeting message.
	 * @throws RemoteException
	 */
	protected GreetingServiceImpl(MessageGenerator messageGenerator) throws RemoteException {
		this.messageGenerator = messageGenerator;
	}

	@Override
	public String computeGreetingMessage(String personName) throws RemoteException {
		return messageGenerator.generateGreetingMessage(personName);
	}

	@Override
	public CompletableFuture<String> computeGreetingMessageAsync(String personName) throws RemoteException {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(30 * 1000);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			return messageGenerator.generateGreetingMessage(personName);
		});
	}
}

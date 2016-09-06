package zc.studdy.rpc.rmi.server.service;

import java.rmi.RemoteException;
import java.util.concurrent.CompletableFuture;

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
public class GreetingServiceImpl implements GreetingService {
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
	public GreetingServiceImpl(MessageGenerator messageGenerator) {
		this.messageGenerator = messageGenerator;
	}

	@Override
	public String computeGreetingMessage(String personName) {
		return messageGenerator.generateGreetingMessage(personName);
	}

	@Override
	public CompletableFuture<String> computeGreetingMessageAsync(String personName) {
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

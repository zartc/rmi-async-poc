package zc.studdy.rpc.rmi.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.CompletableFuture;


/**
 * The interface of the synchronous Hello service.
 */
public interface GreetingService extends Remote {

	/**
	 * A service method that can be called by the client.
	 *
	 * @param personName the name of the person to greet
	 * @return the greeting message.
	 * @throws java.rmi.RemoteException
	 */
	public String computeGreetingMessage(String personName) throws RemoteException;

	/**
	 * The same method but asynchronous.
	 *
	 * @param personName
	 * @return a future
	 * @throws RemoteException
	 */
	public CompletableFuture<String> computeGreetingMessageAsync(String personName) throws RemoteException;
}

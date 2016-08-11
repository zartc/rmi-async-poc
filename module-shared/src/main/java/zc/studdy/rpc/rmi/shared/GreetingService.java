package zc.studdy.rpc.rmi.shared;

/**
 * The interface of the synchronous Hello service.
 */
public interface GreetingService extends java.rmi.Remote {

	/**
	 * A service method that can be called by the client.
	 *
	 * @param personName the name of the person to greet
	 * @return the greeting message.
	 * @throws java.rmi.RemoteException
	 */
	public String computeGreetingMessage(String personName) throws java.rmi.RemoteException;
}

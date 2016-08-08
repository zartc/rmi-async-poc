package zc.studdy.rmi.shared;

/**
 * This is a remote interface for illustrating RMI client callback.
 */
public interface HelloServiceCallback extends java.rmi.Remote {
	/**
	 * Method invoked by the server to make a call back to the client.
	 *
	 * @param message a string for the client to process.
	 * @throws java.rmi.RemoteException
	 */
	public void notify(String message) throws java.rmi.RemoteException;
}

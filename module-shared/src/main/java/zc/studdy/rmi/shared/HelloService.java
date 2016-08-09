package zc.studdy.rmi.shared;

/**
 * This is a remote interface for illustrating RMI client callback.
 */
public interface HelloService extends java.rmi.Remote {

	/**
	 * A service method that can be called by the client.
	 *
	 * @return the hello message.
	 * @throws java.rmi.RemoteException
	 */
	public String sayHello() throws java.rmi.RemoteException;

	/**
	 * Register a callback.
	 *
	 * @param callback the object of the client to be called-back by the server.
	 * @throws java.rmi.RemoteException
	 */
	public void registerCallback(HelloServiceCallback callback) throws java.rmi.RemoteException;

	/**
	 * Ccancel callback registration.
	 *
	 * @param callback
	 * @throws java.rmi.RemoteException
	 */
	public void unregisterCallback(HelloServiceCallback callback) throws java.rmi.RemoteException;
}

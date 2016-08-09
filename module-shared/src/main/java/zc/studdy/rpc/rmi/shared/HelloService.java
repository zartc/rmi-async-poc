package zc.studdy.rpc.rmi.shared;

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
}

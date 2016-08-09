package zc.studdy.rpc.rmi.shared;

public interface ClockService extends java.rmi.Remote {
	/**
	 * This is a remote interface for illustrating RMI client callback.
	 */
	public interface Callback extends java.rmi.Remote {
		/**
		 * Method invoked by the server to make a call back to the client.
		 *
		 * @param message a string for the client to process.
		 * @throws java.rmi.RemoteException
		 */
		public void notify(String message) throws java.rmi.RemoteException;
	}

	/**
	 * Register a callback.
	 *
	 * @param callback the object of the client to be called-back by the server.
	 * @throws java.rmi.RemoteException
	 */
	public void registerCallback(ClockService.Callback callback) throws java.rmi.RemoteException;

	/**
	 * Ccancel callback registration.
	 *
	 * @param callback
	 * @throws java.rmi.RemoteException
	 */
	public void unregisterCallback(ClockService.Callback callback) throws java.rmi.RemoteException;
}

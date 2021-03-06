package zc.studdy.rpc.rmi.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClockService extends Remote {
	/**
	 * This is a remote interface for illustrating RMI client callback.
	 */
	public interface Callback extends Remote {
		/**
		 * Method invoked by the clock server on each clock top.
		 *
		 * @param message a string for the client to process.
		 * @throws java.rmi.RemoteException
		 */
		public void notify(String message) throws RemoteException;
	}


	/**
	 * Subscribe a callback to be notified on each clock top.
	 *
	 * @param callback the object of the client to be called-back by the server.
	 * @throws java.rmi.RemoteException
	 */
	public void subscribe(ClockService.Callback callback) throws RemoteException;

	/**
	 * Cancel callback subscription.
	 *
	 * @param callback
	 * @throws java.rmi.RemoteException
	 */
	public void unsubscribe(ClockService.Callback callback) throws RemoteException;
}

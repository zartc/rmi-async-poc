package zc.studdy.rpc.rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rpc.rmi.shared.ClockService;


/**
 * A very simple callback.
 *
 * @author Pascal
 */
public class MyCallback extends UnicastRemoteObject implements ClockService.Callback {
	private static final long serialVersionUID = 1L;

	protected MyCallback() throws RemoteException {
		super();
	}

	@Override
	public void notify(String message) {
		System.out.println(message);
	}
}

package zc.studdy.rmi.test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rmi.shared.HelloServiceCallback;


/**
 * A very simple callback.
 *
 * @author Pascal
 */
public class MyCallback extends UnicastRemoteObject implements HelloServiceCallback {
	private static final long serialVersionUID = 1L;

	protected MyCallback() throws RemoteException {
		super();
	}

	@Override
	public void notify(String message) {
		System.out.println(message);
	}
}

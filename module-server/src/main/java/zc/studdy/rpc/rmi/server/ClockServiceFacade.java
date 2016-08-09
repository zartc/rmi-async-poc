package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rpc.rmi.server.clock.ClockClientRegistry;
import zc.studdy.rpc.rmi.shared.ClockService;


public class ClockServiceFacade extends UnicastRemoteObject implements ClockService {
	private static final long serialVersionUID = 1L;

	private ClockClientRegistry callbackRegistry;

	protected ClockServiceFacade(ClockClientRegistry callbackRegistry) throws RemoteException {
		super();
		this.callbackRegistry = callbackRegistry;
	}

	@Override
	public void registerCallback(ClockService.Callback callback) throws RemoteException {
		callbackRegistry.registerCallback(callback);
	}

	@Override
	public void unregisterCallback(ClockService.Callback callback) throws RemoteException {
		callbackRegistry.unregisterCallback(callback);
	}

}

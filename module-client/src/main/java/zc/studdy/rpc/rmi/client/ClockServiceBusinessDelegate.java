package zc.studdy.rpc.rmi.client;

import java.rmi.RemoteException;

import zc.studdy.rpc.rmi.shared.ClockService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


public class ClockServiceBusinessDelegate {
	// injected
	private ClockService clockService;

//	@Inject
	public ClockServiceBusinessDelegate(ServiceLocator serviceLocator) {
		// find and cache the remote service
		this.clockService = serviceLocator.locateClockService();
	}

	public void subscribe(ClockService.Callback callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback can not be null");
		}

		try {
			clockService.subscribe(callback);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service unavailable", e);
		}
	}

	public void unsubscribe(ClockService.Callback callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback can not be null");
		}

		try {
			clockService.unsubscribe(callback);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service unavailable", e);
		}
	}
}

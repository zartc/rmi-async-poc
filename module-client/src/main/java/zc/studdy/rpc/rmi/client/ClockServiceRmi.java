package zc.studdy.rpc.rmi.client;

import java.rmi.RemoteException;

import zc.studdy.rpc.rmi.shared.ClockService;


/**
 * A proxy that calls the remote ClockService via RMI and translate RemoteException to
 * RuntimeException.
 *
 * @author Pascal
 */
public class ClockServiceRmi implements ClockService {

	private ClockService remoteClockService;

	/**
	 * Constructor.
	 *
	 * @param remoteClockService
	 */
	public ClockServiceRmi(ClockService remoteClockService) {
		this.remoteClockService = remoteClockService;
	}

	@Override
	public void subscribe(Callback callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback can not be null");
		}

		try {
			remoteClockService.subscribe(callback);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service unavailable", e);
		}
	}

	@Override
	public void unsubscribe(Callback callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback can not be null");
		}

		try {
			remoteClockService.unsubscribe(callback);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service unavailable", e);
		}
	}
}

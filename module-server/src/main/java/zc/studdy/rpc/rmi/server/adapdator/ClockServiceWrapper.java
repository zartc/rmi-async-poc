package zc.studdy.rpc.rmi.server.adapdator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rpc.rmi.shared.ClockService;


/**
 * Wrap the ClockService and adapt to the choosen remote access technologie (RMI in this
 * case, but other implementations could be developped : REST, SOAP, JMS, Akka, etc). That
 * way, multiple wapper can expose the service class through different remote access
 * thechnologie while the service class stay unpoluted by these purely technical concerns.
 *
 * @author Pascal
 */
public class ClockServiceWrapper extends UnicastRemoteObject implements ClockService {
	private static final long serialVersionUID = 1L;

	private ClockService clockService;

	public ClockServiceWrapper(ClockService clockService) throws RemoteException {
		this.clockService = clockService;
	}

	@Override
	public void subscribe(ClockService.Callback callback) throws RemoteException {
		clockService.subscribe(callback);
	}

	@Override
	public void unsubscribe(ClockService.Callback callback) throws RemoteException {
		clockService.unsubscribe(callback);
	}

	public ClockService unwrap() {
		return clockService;
	}
}

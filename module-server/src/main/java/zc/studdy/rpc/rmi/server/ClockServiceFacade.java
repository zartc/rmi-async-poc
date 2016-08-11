package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import zc.studdy.rpc.rmi.shared.ClockService;


/**
 * The Facade expose the Service to the client through the choosen remote access
 * technologie (RMI in this case, but other implementations could be developped : REST,
 * SOAP, JMS, Akka, etc).
 *
 * @author Pascal
 */
public class ClockServiceFacade extends UnicastRemoteObject implements ClockService {
	private static final long serialVersionUID = 1L;

	private ClockService clockService;

	protected ClockServiceFacade(ClockService clockService) throws RemoteException {
		super();
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

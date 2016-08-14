package zc.studdy.rpc.rmi.client;

import java.rmi.RemoteException;

import zc.studdy.rpc.rmi.shared.GreetingService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


public class GreetingBusinessDelegate {
	// injected
	private GreetingService greetingService;

//	@Inject
	public GreetingBusinessDelegate(ServiceLocator serviceLocator) {
		// find and cache the remote service
		this.greetingService = serviceLocator.locateGreetingService();
	}

	public String getGreetingMessage(String personName) {
		try {
			return greetingService.computeGreetingMessage(personName);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service unavailable", e);
		}
	}
}

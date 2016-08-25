package zc.studdy.rpc.rmi.shared;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The ServiceLocator hides the complexity of registering and locating remote services. It
 * is the only class in the system that knows where the registry is located and the only
 * one that knows the names under which the services are registered.
 * <p>
 * The class is shared between the server and the client so that they both share the same
 * information/configuration.
 *
 * @author Pascal
 */
public class ServiceLocator {
	private static final Logger log = LoggerFactory.getLogger(ServiceLocator.class);

	private static final String GREETING_SERVICE_NAME = "zc.studdy.rpc.rmi.greeting-service.";
	private static final String CLOCK_SERVICE_NAME = "zc.studdy.rpc.rmi.clock-service";

	private String rmiRegistryURL;


	public ServiceLocator(String registryHost, int registryPort) {
		this.rmiRegistryURL = "rmi://" + registryHost + ":" + String.valueOf(registryPort);
	}


	public void registerGreetingService(Remote service) {
		registerService(GREETING_SERVICE_NAME, service);
	}

	public void registerClockService(Remote service) {
		registerService(CLOCK_SERVICE_NAME, service);
	}

	public GreetingService locateGreetingService() throws ServiceLocatorException {
		return locateService(rmiRegistryURL + GREETING_SERVICE_NAME);
	}

	public ClockService locateClockService() throws ServiceLocatorException {
		return locateService(rmiRegistryURL + CLOCK_SERVICE_NAME);
	}


	private void registerService(String path, Remote service) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		String url = rmiRegistryURL + path;

		try {
			Naming.rebind(url, service);
			log.info("Service registered on: {}", url);
		}
		catch (MalformedURLException e) {
			throw new ServiceLocatorException("unable to register service", e)
					.addContextValue("url", url);
		}
		catch (RemoteException e) {
			throw new ServiceLocatorException("unable to register service", e.getCause())
					.addContextValue("url", url);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends java.rmi.Remote> T locateService(String url) throws ServiceLocatorException {
		try {
			return (T)Naming.lookup(url);
		}
		catch (MalformedURLException | NotBoundException e) {
			throw new ServiceLocatorException("service not found", e)
					.addContextValue("url", url);
		}
		catch (RemoteException e) {
			throw new ServiceLocatorException("service not found", e.getCause())
					.addContextValue("url", url);
		}
	}
}

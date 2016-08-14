package zc.studdy.rpc.rmi.shared;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The ServiceLocator hides the complexity of locating and registering the remote
 * services.
 *
 * @author Pascal
 */
public class ServiceLocator {
//	@Value("${registryPort}")
	private int registryPort;

//	@Value("${registryHost}")
	private String registryHost;

//	@Value("${greetingServicePath}")
	private String greetingServiceURI = "/greeting";

//	@Value("${clockServicePath}")
	private String clockServiceURI = "/clock";

	private String rmiRegistryURL;


//	@Inject
	public ServiceLocator(String registryHost, int registryPort) {
		this.registryPort = registryPort;
		this.registryHost = registryHost;
		this.rmiRegistryURL = "rmi://" + registryHost + ":" + String.valueOf(registryPort);
	}

	public void registerGreetingService(Remote service) {
		registerService(greetingServiceURI, service);
	}

	public void registerClockService(Remote service) {
		registerService(clockServiceURI, service);
	}

	private void registerService(String path, Remote service) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		String url = rmiRegistryURL + path;

		try {
			Naming.rebind(url, service);
			System.out.println("Service registered on: " + url);
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

	public GreetingService locateGreetingService() throws ServiceLocatorException {
		return locateService(rmiRegistryURL + greetingServiceURI);
	}

	public ClockService locateClockService() throws ServiceLocatorException {
		return locateService(rmiRegistryURL + clockServiceURI);
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

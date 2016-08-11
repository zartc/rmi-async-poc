package zc.studdy.rpc.rmi.shared;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


/**
 * The ServiceLocator hides the complexity of locating and registering the remote
 * services.
 *
 * @author Pascal
 */
public class ServiceLocator {

	private int registryPort;
	private String registryHost;

	private String rmiRegistryURL;
	private String helloServiceURI = "/hello";
	private String clockServiceURI = "/clock";


	public ServiceLocator(String registryHost, int registryPort) {
		this.registryPort = registryPort;
		this.registryHost = registryHost;
		this.rmiRegistryURL = "rmi://" + registryHost + ":" + String.valueOf(registryPort);
	}

	public String getRegistryHost() {
		return registryHost;
	}

	public int getRegistryPort() {
		return registryPort;
	}

	public String getHelloServiceUrl() {
		return rmiRegistryURL + helloServiceURI;
	}

	public String getClockServiceUrl() {
		return rmiRegistryURL + clockServiceURI;
	}

	public GreetingService locateHelloService() {
		return locateService(getHelloServiceUrl());
	}

	public ClockService locateClockService() {
		return locateService(getClockServiceUrl());
	}

	@SuppressWarnings("unchecked")
	private <T extends java.rmi.Remote> T locateService(String url) {
		try {
			// find the remote service
			return (T)Naming.lookup(url);
		}
		catch (MalformedURLException | NotBoundException e) {
			throw new RuntimeException("service not found at the given url: " + url, e);
		}
		catch (RemoteException e) {
			throw new RuntimeException("service not found at the given url: " + url, e.getCause());
		}
	}
}

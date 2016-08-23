package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import zc.studdy.rpc.rmi.server.config.ServerProperties;
import zc.studdy.rpc.rmi.shared.ClockService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


/**
 * The main class that instanciate the service classes, bind them into the RMI registry
 * and start the Server.
 *
 * @author Pascal
 * @See http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html
 */
@SpringBootApplication
@EnableConfigurationProperties(ServerProperties.class)
@EnableScheduling
public class Server implements ApplicationRunner {

	private int port;
	private String host;

	public Server(ServerProperties serverProperties) {
		this.host = serverProperties.getHost();
		this.port = serverProperties.getPort();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if ("localhost".equalsIgnoreCase(host)) {
			// starts a RMI registry on the localhost, if it does not already exists at the specified port number.
			try {
				Registry registry = LocateRegistry.getRegistry(port);
				// This call will throw an exception
				// if the registry does not already exist
				registry.list();
			}
			catch (RemoteException e) {
				// No valid registry at that port.
				LocateRegistry.createRegistry(port);
			}
		}

		// bind a ClockServiceProxy into the RMI registry
		serviceLocaltor().registerClockService(new ClockServiceProxy(clockService()));
	}

	@Bean
	ServiceLocator serviceLocaltor() {
		return new ServiceLocator("localhost", port);
	}

	@Bean
	ClockService clockService() {
		return new ClockServiceImpl();
	}


	// ----- MAIN -----

	public static void main(String args[]) throws RemoteException {
		new SpringApplicationBuilder(Server.class).web(false).run(args);
	}
}

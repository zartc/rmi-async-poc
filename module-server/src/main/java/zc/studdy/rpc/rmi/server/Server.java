package zc.studdy.rpc.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import zc.studdy.rpc.rmi.config.RmiServerProperties;
import zc.studdy.rpc.rmi.server.adapdator.ClockServiceWrapper;
import zc.studdy.rpc.rmi.server.adapdator.GreetingServiceWrapper;
import zc.studdy.rpc.rmi.server.config.ClockServiceProperties;
import zc.studdy.rpc.rmi.server.service.ClockServiceImpl;
import zc.studdy.rpc.rmi.server.service.GreetingServiceImpl;
import zc.studdy.rpc.rmi.server.service.MessageGeneratorSimple;
import zc.studdy.rpc.rmi.shared.ClockService;
import zc.studdy.rpc.rmi.shared.GreetingService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


/**
 * The main class that instanciate the service classes, bind them into the RMI registry
 * and start the Server.
 *
 * @author Pascal
 * @See http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html
 * @see http://barakb.github.io/asyncrmi/
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@PropertySource("classpath:/rmi-server.properties")
public class Server implements ApplicationRunner {
	private static final Logger log = LoggerFactory.getLogger(Server.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		startRmiServer();

		// bind a ClockService and the GreetingService into the RMI registry
		serviceLocaltor().registerClockService(clockService());
		serviceLocaltor().registerGreetingService(greetingService());

		log.debug("delai = {}", clockServiceProperties().getInitialDelay());
		log.debug("rate  = {}", clockServiceProperties().getRate());
	}

	private void startRmiServer() throws RemoteException {
		RmiServerProperties rmiServerPropertie = rmiServerPropertie();

		if ("localhost".equalsIgnoreCase(rmiServerPropertie.getHost())) {
			int port = rmiServerPropertie.getPort();

			try {
				// starts a RMI registry on the localhost, if it does not already exists at the specified port number.
				Registry registry = LocateRegistry.getRegistry(port);
				// This call will throw an exception if the registry does not already exist
				registry.list();
			}
			catch (RemoteException e) {
				// No valid registry at that port.
				LocateRegistry.createRegistry(port);
			}
		}
	}

	@Bean
	RmiServerProperties rmiServerPropertie() {
		return new RmiServerProperties();
	}

	@Bean
	ClockServiceProperties clockServiceProperties() {
		return new ClockServiceProperties();
	}

	@Bean
	ServiceLocator serviceLocaltor() {
		RmiServerProperties rmiServerPropertie = rmiServerPropertie();
		String host = rmiServerPropertie.getHost();
		int port = rmiServerPropertie.getPort();
		return new ServiceLocator(host, port);
	}

	@Bean
	ClockService clockService() throws RemoteException {
		return new ClockServiceWrapper(new ClockServiceImpl());
	}

	@Bean
	GreetingService greetingService() throws RemoteException {
		return new GreetingServiceWrapper(new GreetingServiceImpl(new MessageGeneratorSimple()));
	}


	// ----- MAIN -----

	public static void main(String args[]) throws RemoteException {
		new SpringApplicationBuilder(Server.class).web(false).run(args);
	}
}

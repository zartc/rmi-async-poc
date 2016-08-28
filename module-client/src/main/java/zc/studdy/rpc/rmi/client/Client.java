package zc.studdy.rpc.rmi.client;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import zc.studdy.rpc.rmi.config.RmiServerProperties;
import zc.studdy.rpc.rmi.shared.ClockService;
import zc.studdy.rpc.rmi.shared.GreetingService;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


// http://www.baeldung.com/2012/02/06/properties-with-spring/?utm_source=email-newsletter&utm_medium=email&utm_campaign=auto_7_spring

@SpringBootApplication
@PropertySource("classpath:/rmi-server.properties")
public class Client implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(Client.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// make a synchronous call
		String message = greetingService().computeGreetingMessage("Pascal");
		log.info("Server found, it says: {}", message);

		// then create a Callback to pass to the server for it to call us back asynchronously
		MyCallback callback = new MyCallback();
		clockService().subscribe(callback);
		log.info("Just registered the callback.");

		try {
			Thread.sleep(30 * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		clockService().unsubscribe(callback);
		log.info("Just unregistered the callback.");

	}

	@Bean
	RmiServerProperties rmiServerPropertie() {
		return new RmiServerProperties();
	}

	@Bean
	ServiceLocator serviceLocaltor() {
		RmiServerProperties rmiServerPropertie = rmiServerPropertie();
		String host = rmiServerPropertie.getHost();
		int port = rmiServerPropertie.getPort();
		return new ServiceLocator(host, port);
	}

	@Bean
	GreetingService greetingService() {
		ServiceLocator serviceLocaltor = serviceLocaltor();
		GreetingService greetingService = serviceLocaltor.locateGreetingService();
		return new GreetingServiceRmi(greetingService);
	}

	@Bean
	ClockService clockService() {
		ServiceLocator serviceLocaltor = serviceLocaltor();
		ClockService clockService = serviceLocaltor.locateClockService();
		return new ClockServiceRmi(clockService);
	}

	@Bean
	TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(10);
		taskExecutor.setMaxPoolSize(20);
		taskExecutor.setQueueCapacity(25);
		return taskExecutor;
	}


	// ----- MAIN -----

	public static void main(String args[]) throws RemoteException {
		new SpringApplicationBuilder(Client.class).web(false).run(args);
	}
}

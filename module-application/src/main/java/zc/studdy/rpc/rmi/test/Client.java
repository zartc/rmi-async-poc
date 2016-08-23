package zc.studdy.rpc.rmi.test;

import java.rmi.RemoteException;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import zc.studdy.rpc.rmi.client.ClockServiceBusinessDelegate;
import zc.studdy.rpc.rmi.client.GreetingBusinessDelegate;
import zc.studdy.rpc.rmi.shared.ServiceLocator;


// http://www.baeldung.com/2012/02/06/properties-with-spring/?utm_source=email-newsletter&utm_medium=email&utm_campaign=auto_7_spring

@SpringBootApplication
public class Client implements ApplicationRunner {

	private GreetingBusinessDelegate greetingBusinessDelegate;
	private ClockServiceBusinessDelegate clockServiceBusinessDelegate;

	/**
	 * Constructor.
	 *
	 * @param greetingBusinessDelegate
	 * @param clockServiceBusinessDelegate
	 */
	@Inject
	public Client(GreetingBusinessDelegate greetingBusinessDelegate, ClockServiceBusinessDelegate clockServiceBusinessDelegate) {
		this.greetingBusinessDelegate = greetingBusinessDelegate;
		this.clockServiceBusinessDelegate = clockServiceBusinessDelegate;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		List<String> nonOptionArgs = args.getNonOptionArgs();
//		int port = parseServicePort(nonOptionArgs.size() > 0 ? nonOptionArgs.get(0) : null);

		// make a synchronous call
		System.out.println("Server found, it says: " + greetingBusinessDelegate.getGreetingMessage("Pascal"));

		// then create a Callback to pass to the server for it to call us back asynchronously
		MyCallback callback = new MyCallback();
		clockServiceBusinessDelegate.subscribe(callback);
		System.out.println("Just registered the callback.");

		try {
			Thread.sleep(30 * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		clockServiceBusinessDelegate.unsubscribe(callback);
		System.out.println("Just unregistered the callback.");
	}

	private static int parseServicePort(String args) {
		try {
			return Integer.parseInt(args);
		}
		catch (NumberFormatException e) {
			return 1099;
		}
	}

	@Bean
	ServiceLocator getServiceLocaltor() {
		return new ServiceLocator("localhost", 1099);
	}

	@Bean
	GreetingBusinessDelegate getGreetingBusinessDelegate() {
		return new GreetingBusinessDelegate(getServiceLocaltor());
	}

	@Bean
	ClockServiceBusinessDelegate getClockServiceBusinessDelegate() {
		return new ClockServiceBusinessDelegate(getServiceLocaltor());
	}


	// ----- MAIN -----

	public static void main(String args[]) throws RemoteException {
		new SpringApplicationBuilder(Client.class).web(false).run(args);
		System.exit(0);
	}
}

package zc.studdy.rpc.rmi.server.clock.impl;

import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import zc.studdy.rpc.rmi.server.clock.ClockClientRegistry;


/**
 * The implementation of the synchronous ClocService. As you can see it only deal with the
 * calling of the registered callback. The un/registering of the callback proper is
 * handled by the ClockClientRegistry (separation of concern).
 *
 * @author Pascal
 */
public class ClockServiceImpl implements Runnable {

	private ClockClientRegistry callbackRegistry;
	private ScheduledExecutorService executor;


	public ClockServiceImpl(ClockClientRegistry callbackRegistry) {
		this.callbackRegistry = callbackRegistry;

		this.executor = Executors.newScheduledThreadPool(1);
		this.executor.scheduleAtFixedRate(this, 5, 2, TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		try {
			System.out.println("HelloService tic-tac");
			String dateText = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);

			callbackRegistry.forEach(callback -> {
				try {
					callback.notify("AsynchronousHelloServiceImpl => le bonsoir de " + dateText);
				}
				catch (RemoteException e) {
					e.printStackTrace();
					throw new RuntimeException("found a DEAD callback");
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}

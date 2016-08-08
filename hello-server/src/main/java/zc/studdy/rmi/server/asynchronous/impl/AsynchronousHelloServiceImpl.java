package zc.studdy.rmi.server.asynchronous.impl;

import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import zc.studdy.rmi.server.asynchronous.HelloCallbackRegistry;


/**
 * The implementation of the AsynchronousHelloService. As you can see it only deal with
 * the calling of the registered callback. The un/registering of the callback proper is
 * handled by the HelloCallbackRegistry (separation of concern).
 *
 * @author Pascal
 */
public class AsynchronousHelloServiceImpl implements Runnable {

	private HelloCallbackRegistry callbackRegistry;
	private ScheduledExecutorService executor;


	public AsynchronousHelloServiceImpl(HelloCallbackRegistry callbackRegistry) {
		this.callbackRegistry = callbackRegistry;

		this.executor = Executors.newScheduledThreadPool(1);
		this.executor.scheduleAtFixedRate(this, 5, 2, TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		try {
			System.out.println("HelloService tic");
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

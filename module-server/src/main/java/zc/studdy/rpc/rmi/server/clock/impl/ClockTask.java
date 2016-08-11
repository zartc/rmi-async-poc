package zc.studdy.rpc.rmi.server.clock.impl;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ClockTask implements Runnable {

	/**
	 * The interface signaled by the Task.
	 *
	 * @author Pascal
	 */
	public interface Observer {
		void tickSignal(Instant instant);
	}


	private Observer observer;
	private ScheduledExecutorService executor;

	/**
	 * Constructor.
	 *
	 * @param observer an observer instance that the clock service will notify on every
	 *        tick.
	 * @param initialDelay the delay before the closk start ticking.
	 * @param period the period between successive ticks.
	 * @param unit the time unit of the initialDelay and period parameters.
	 */
	public ClockTask(Observer observer, long initialDelay, long period, TimeUnit unit) {
		this.observer = observer;
		this.executor = Executors.newScheduledThreadPool(1);
		this.executor.scheduleAtFixedRate(this, initialDelay, period, unit);
	}

	@Override
	public void run() {
		observer.tickSignal(Instant.now());
	}
}

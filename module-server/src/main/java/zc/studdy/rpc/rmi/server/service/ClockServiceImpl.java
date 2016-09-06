package zc.studdy.rpc.rmi.server.service;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import zc.studdy.rpc.rmi.shared.ClockService;


/**
 * The ClockServiceImpl job is to manage the collection of callback registered by clients.
 * Since clients can un/register at any given time asynchronously even while iteration is
 * taking place, precautions must be taken to prevent errors and non-deterministic
 * behavior.
 *
 * @author Pascal
 */
public class ClockServiceImpl implements ClockService {
	private static final Logger log = LoggerFactory.getLogger(ClockServiceImpl.class);

	private final List<ClockService.Callback> callbacks = Collections.synchronizedList(new ArrayList<>());

	@Override
	public void subscribe(ClockService.Callback callback) {
		synchronized (callbacks) {
			if (!(callbacks.contains(callback))) {
				callbacks.add(callback);
				log.info("ClockService.subscribe just registered a new callback. "
						+ "Now having {} callbacks registered.", callbacks.size());
			}
		}
	}

	@Override
	public void unsubscribe(ClockService.Callback callback) {
		synchronized (callbacks) {
			if (callbacks.remove(callback)) {
				log.info("ClockService.unsubscribe just unregistered a callback. "
						+ "Now having {} callbacks left.", callbacks.size());
			}
			else {
				log.info("ClockService.unsubscribe: callback wasn't registered.");
			}
		}
	}

	@Scheduled(initialDelayString = "${clock-service.initial-delay}", fixedRateString = "${clock-service.rate}")
	public void beat() {
		log.info("ClockService tickSignal");

		LocalTime localTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalTime();
		String dateText = localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
		String message = "ClockService tick @ " + dateText;

		synchronized (callbacks) {
			for (Iterator<ClockService.Callback> iterator = callbacks.iterator(); iterator.hasNext();) {
				try {
					iterator.next().notify(message);
				}
				catch (Exception e) {
					iterator.remove();
					log.info("ClockService just detected and removed a DEAD callback. "
							+ "Now having {} callbacks left.", callbacks.size());
				}
			}
		}
	}
}

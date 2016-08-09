package zc.studdy.rpc.rmi.server.clock.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import zc.studdy.rpc.rmi.server.clock.ClockClientRegistry;
import zc.studdy.rpc.rmi.shared.ClockService;


/**
 * The ClientRegistry job is to manage the collection of callback registered by clients.
 * Since clients can un/register at any given time asynchronously even while iteration
 * take place, precautions must be taken to prevent errors and non-deterministic behavior.
 *
 * @author Pascal
 */
public class ClockClientRegistryImpl implements ClockClientRegistry {
	private List<ClockService.Callback> callbacks = Collections.synchronizedList(new ArrayList<>());

	@Override
	public void registerCallback(ClockService.Callback callback) {
		synchronized (callbacks) {
			if (!(callbacks.contains(callback))) {
				callbacks.add(callback);
				System.out.println("ClockClientRegistryImpl just registered a new callback. "
						+ "Now having " + callbacks.size() + " callbacks registered.");
			}
		}
	}

	@Override
	public void unregisterCallback(ClockService.Callback callback) {
		synchronized (callbacks) {
			if (callbacks.remove(callback)) {
				System.out.println("ClockClientRegistryImpl just unregistered a callback. "
						+ "Now having " + callbacks.size() + " callbacks left.");
			}
			else {
				System.out.println("ClockClientRegistryImpl.unregister: callback wasn't registered.");
			}
		}
	}

	@Override
	public void forEach(Consumer<? super ClockService.Callback> action) {
		Objects.requireNonNull(action);
		synchronized (callbacks) {
			for (Iterator<ClockService.Callback> iterator = callbacks.iterator(); iterator.hasNext();) {
				ClockService.Callback callback = iterator.next();
				try {
					action.accept(callback);
				}
				catch (Exception e) {
					iterator.remove();
					System.out.println("ClockClientRegistryImpl just removed a DEAD callback. "
							+ "Now having " + callbacks.size() + " callbacks left.");
				}
			}
		}
	}
}

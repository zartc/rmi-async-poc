package zc.studdy.rmi.server.asynchronous.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import zc.studdy.rmi.server.asynchronous.HelloCallbackRegistry;
import zc.studdy.rmi.shared.HelloServiceCallback;


/**
 * The callbackRegistry job is to manage the collection of callback registered by clients.
 * Since clients can un/register at any given time asynchronously even while iteration
 * take place, precautions must be taken to prevent errors and non-deterministic behavior.
 *
 * @author Pascal
 */
public class HelloCallbackRegistryImpl implements HelloCallbackRegistry {
	private List<HelloServiceCallback> callbacks = Collections.synchronizedList(new ArrayList<>());

	@Override
	public void registerCallback(HelloServiceCallback callback) {
		synchronized (callbacks) {
			if (!(callbacks.contains(callback))) {
				callbacks.add(callback);
				System.out.println("HelloServiceImpl just registered a new callback. "
						+ "Now having " + callbacks.size() + " callbacks registered.");
			}
		}
	}

	@Override
	public void unregisterCallback(HelloServiceCallback callback) {
		synchronized (callbacks) {
			if (callbacks.remove(callback)) {
				System.out.println("HelloServiceImpl just unregistered a callback. "
						+ "Now having " + callbacks.size() + " callbacks left.");
			}
			else {
				System.out.println("HelloServiceImpl.unregister: callback wasn't registered.");
			}
		}
	}

	@Override
	public void forEach(Consumer<? super HelloServiceCallback> action) {
		Objects.requireNonNull(action);
		synchronized (callbacks) {
			for (Iterator<HelloServiceCallback> iterator = callbacks.iterator(); iterator.hasNext();) {
				HelloServiceCallback callback = iterator.next();
				try {
					action.accept(callback);
				}
				catch (Exception e) {
					iterator.remove();
					System.out.println("HelloServiceImpl just removed a DEAD callback. "
							+ "Now having " + callbacks.size() + " callbacks left.");
				}
			}
		}
	}
}

package zc.studdy.rmi.server.asynchronous;

import java.util.function.Consumer;

import zc.studdy.rmi.shared.HelloServiceCallback;


public interface HelloCallbackRegistry {

	void registerCallback(HelloServiceCallback callback);

	void unregisterCallback(HelloServiceCallback callback);

	void forEach(Consumer<? super HelloServiceCallback> action);
}

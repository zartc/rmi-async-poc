package zc.studdy.rpc.rmi.server.clock;

import java.util.function.Consumer;

import zc.studdy.rpc.rmi.shared.ClockService;


public interface ClockClientRegistry {

	void registerCallback(ClockService.Callback callback);

	void unregisterCallback(ClockService.Callback callback);

	void forEach(Consumer<? super ClockService.Callback> action);
}

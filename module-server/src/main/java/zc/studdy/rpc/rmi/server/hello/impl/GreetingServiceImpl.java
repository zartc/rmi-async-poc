package zc.studdy.rpc.rmi.server.hello.impl;

import zc.studdy.rpc.rmi.shared.GreetingService;


/**
 * The HelloService implementation. It could not be much simpler.
 *
 * @author Pascal
 */
public class GreetingServiceImpl implements GreetingService {

	private int callerCount = 1;

	@Override
	public String computeGreetingMessage(String name) {
		return ("hello " + name + ", you are the " + callerCount++ + " greeted today");
	}
}

package zc.studdy.rpc.rmi.server.hello.impl;

import zc.studdy.rpc.rmi.shared.HelloService;


/**
 * The HelloService implementation. It could not be much simpler.
 *
 * @author Pascal
 */
public class HelloServiceImpl implements HelloService {

	private int callerCount = 1;

	@Override
	public String sayHello() {
		return ("hello " + callerCount++);
	}
}

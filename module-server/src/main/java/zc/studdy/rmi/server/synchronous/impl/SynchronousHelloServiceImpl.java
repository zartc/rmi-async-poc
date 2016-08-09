package zc.studdy.rmi.server.synchronous.impl;

import zc.studdy.rmi.server.synchronous.SynchronousHelloService;


/**
 * The SynchronousHelloService implementation. It could not be much simpler.
 *
 * @author Pascal
 */
public class SynchronousHelloServiceImpl implements SynchronousHelloService {

	private int callerCount = 1;

	@Override
	public String sayHello() throws java.rmi.RemoteException {
		return ("hello " + callerCount++);
	}
}

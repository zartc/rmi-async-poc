package zc.studdy.rpc.rmi.server.service;

/**
 * The class implementing the methods published by the GreetingService interface.
 * <P>
 * Note that this class does not implements the GreetingService interface, indeed this is
 * the GreetingServiceAdapter that implements that interface and it uses this class as the
 * internal implementation. Implementation and interface are free to evoluate, this is a
 * bridge impkementation.
 *
 * @author Pascal
 */
public class MessageGeneratorSimple implements MessageGenerator {

	private int callerCount = 1;

	@Override
	public String generateGreetingMessage(String personName) {
		return ("hello " + personName + ", you are caller #" + callerCount++);
	}
}

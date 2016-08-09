package zc.studdy.rmi.server.synchronous;

import java.rmi.RemoteException;


/**
 * @author Pascal
 */
public interface SynchronousHelloService {

	String sayHello() throws RemoteException;
}

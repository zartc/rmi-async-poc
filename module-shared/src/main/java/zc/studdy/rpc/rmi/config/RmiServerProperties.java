package zc.studdy.rpc.rmi.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author Pascal
 * @see http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties
 */
@ConfigurationProperties(prefix = "rmi.server")
public class RmiServerProperties {
	/**
	 * IP of the server
	 */
	private String host = "localhost";

	/**
	 * The RMI port number
	 */
	private int port = 1099;


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

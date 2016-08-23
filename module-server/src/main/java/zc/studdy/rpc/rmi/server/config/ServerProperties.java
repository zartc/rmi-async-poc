package zc.studdy.rpc.rmi.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author Pascal
 * @see http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties
 */
@ConfigurationProperties(prefix = "rmi.server")
public class ServerProperties {
	/**
	 * IP of the server
	 */
	private String host = "localhost";
	/**
	 * The RMI port number
	 */
	private int port = 1099;

	private Clock clockService;

	static public class Clock {
		/**
		 * The initial delay before the clock task start
		 */
		private int initialDelay = 10000;
		/**
		 * the rate of the clock task
		 */
		private int rate = 5000;

		public int getInitialDelay() {
			return initialDelay;
		}

		public void setInitialDelay(int initialDelay) {
			this.initialDelay = initialDelay;
		}

		public int getRate() {
			return rate;
		}

		public void setRate(int rate) {
			this.rate = rate;
		}
	}

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

	public Clock getClockService() {
		return clockService;
	}

	public void setClockService(Clock clockService) {
		this.clockService = clockService;
	}

}

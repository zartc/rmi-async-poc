package zc.studdy.rpc.rmi.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "clock-service")
public class ClockServiceProperties {
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

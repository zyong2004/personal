package redis.pubsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class Publisher {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Publisher.class);

	private final Jedis publisherJedis;

	private final String channel;

	public Publisher(Jedis publisherJedis, String channel) {
		this.publisherJedis = publisherJedis;
		this.channel = channel;
	}

	public void startPublish() {
		LOGGER.info("type you message(quit for teminate)");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));

			while (true) {

				String line = reader.readLine();

				if (!"quit".equals(line)) {
					publisherJedis.publish(channel, line);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			LOGGER.error("error", e);
		}
	}
}

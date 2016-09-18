package redis.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

public class Subcriber extends JedisPubSub {

	private static Logger LOGGER = LoggerFactory.getLogger(Subcriber.class);
	@Override
	public void onMessage(String channel, String message) {

		LOGGER.info("Message. Channel: {}, Msg: {}", channel, message);
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		LOGGER.info("PMessage. Pattern: {}, Channel: {}, Msg: {}", 
	    	    pattern, channel, message);

	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		LOGGER.info("onPSubscribe");

	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		LOGGER.info("onPUnsubscribe");

	}

	@Override
	public void onSubscribe(String arg0, int arg1) {
		LOGGER.info("onSubscribe");

	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		LOGGER.info("onUnsubscribe");

	}

}

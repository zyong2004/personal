package redis.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * redis 订阅/发布模式测试
 * 参见 https://my.oschina.net/itblog/blog/601284
 * @author zhangyong
 *
 */
public class MainTest {

	
	public static final String CHANNEL_NAME = "MyChannel";
	
	public static final String REDIS_HOST ="localhost";
	
	public static final int REDIS_PORT =6379;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);
	
	private static final JedisPoolConfig POOL_CONFIG = new JedisPoolConfig();
	
	private static final JedisPool JEDIS_POOL= new JedisPool(POOL_CONFIG,REDIS_HOST, REDIS_PORT);
	
	public static void main(String[] args) {
		
		
		final Jedis subcriberJedis = JEDIS_POOL.getResource();
		
		final Jedis publisherJedis = JEDIS_POOL.getResource();
		
		final Subcriber subcriber = new Subcriber();
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			
				try {
			
					LOGGER.info("SUBCRIBING to 'MyChannel' .this thread will be blocked");
					
					subcriberJedis.subscribe(subcriber, CHANNEL_NAME);
					
					LOGGER.info("Subscription end");
					
				} catch (Exception e) {
					LOGGER.error("ERROR" ,e);
				}
			}
		}).start();;
		
		new Publisher(publisherJedis, CHANNEL_NAME).startPublish();
		
		publisherJedis.close();
		subcriber.unsubscribe();
		subcriberJedis.close();
	}
}


package redis;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import jodd.util.CollectionUtil;
import redis.clients.jedis.Jedis;

public class RedisTest {

	@Test
	public void test1(){

		
		Jedis jedis = new Jedis("localhost", 6379, 20000);
		Map<String,String> map =jedis.hgetAll("test-test-test");
		Set<Entry<String, String>>set = map.entrySet();
		for (Entry<String, String> entry : set) {
			jedis.hdel("test-test-test", entry.getKey());	
		}
	}
	@Test
	public void test2(){
		Jedis jedis = new Jedis("localhost", 6379, 20000);
		jedis.hset("test-test-test", "test_field", "test_value");
		jedis.hset("test-test-test", "test_field1", "test_value");
		jedis.hset("test-test-test", "test_field2", "test_value");
		jedis.hset("test-test-test", "test_field3", "test_value");
		jedis.expire("test-test-test", 30*60);
		jedis.close();
	}
	@Test
	public void test3(){
		RedisClient client = new RedisClient();
		client.show();
	}
}

package jms.test;

import javax.jms.Destination;

import jms.TopicMessageConsumer;
import jms.TopicMessageProducer;
import jms.TopicProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jms/jms1.xml")
public class SpringJmsTest {
	  /**
     * 队列名queue1
     */
    @Autowired
    private Destination queueDestination;
 
    /**
     * 队列名queue2
     */
    @Autowired
    private Destination queueDestination2;
 
    /**
     * 主题 guo_topic
     */
    @Autowired
    @Qualifier("topicDestination")
    private Destination topic;
 
    /**
     * 主题消息发布者
     */
    @Autowired
    private TopicProvider topicProvider;
 
    /**
     * 队列消息生产者
     */
    @Autowired
    @Qualifier("producerService")
    private TopicMessageProducer producer;
 
    /**
     * 队列消息生产者
     */
    @Autowired
    @Qualifier("consumerService")
    private TopicMessageConsumer consumer;
 
    /**
     * 测试生产者向queue1发送消息
     */
    @Test
    public void testProduce() {
        String msg = "Hello world!";
        producer.sendMessage(msg);
    }
 
    /**
     * 测试消费者从queue1接受消息
     */
    @Test
    public void testConsume() {
        consumer.receive(queueDestination);
    }
 
    /**
     * 测试消息监听
     * 
     * 1.生产者向队列queue2发送消息
     * 
     * 2.ConsumerMessageListener监听队列，并消费消息
     */
    @Test
    public void testSend() {
        producer.sendMessage(queueDestination2, "Hello China~~~~~~~~~~~~~~~");
    }
 
    /**
     * 测试主题监听
     * 
     * 1.生产者向主题发布消息
     * 
     * 2.ConsumerMessageListener监听主题，并消费消息
     */
    @Test
    public void testTopic() throws Exception {
        topicProvider.publicTopic(topic, "Hello T-To-Top-Topi-Topic!");
    }
}

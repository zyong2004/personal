package jms;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class TopicProvider {
	private Logger logger = LoggerFactory.getLogger(TopicProvider.class);
	private JmsTemplate topicJmsTemplate;
	public void publicTopic(final Destination topic, final String msg){
		
		logger.info("detination:{}",topic);
		logger.info("msg:{}",msg);
		System.out.println(""+topic);
		System.out.println("msg:"+msg);
		 System.out.println("topic name 是" + topic.toString()
                 + "，发布消息内容为:\t" + msg);
		topicJmsTemplate.convertAndSend(topic, msg);
		
	}
	public JmsTemplate getTopicJmsTemplate() {
		return topicJmsTemplate;
	}
	public void setTopicJmsTemplate(JmsTemplate topicJmsTemplate) {
		this.topicJmsTemplate = topicJmsTemplate;
	}
	
}

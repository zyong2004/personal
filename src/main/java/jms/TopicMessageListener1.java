package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicMessageListener1 implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(TopicMessageListener1.class);
	@Override
	public void onMessage(Message message) {
		 try {
			 TextMessage tm = (TextMessage) message;
			 System.out.println("TopicMessageListener \t" + tm.getText());
			 logger.info("TopicMessageListener \t" + tm.getText());
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
	}

}

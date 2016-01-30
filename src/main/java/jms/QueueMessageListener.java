package jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueMessageListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);
	@Override
	public void onMessage(Message arg0) {
		System.out.println(1111);
		logger.info("{}",arg0);
	}

}

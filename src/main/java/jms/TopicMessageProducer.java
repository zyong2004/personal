package jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TopicMessageProducer {
	private JmsTemplate jmsTemplate;
	
	
	public void sendMessage(Destination destination, final String msg){
		  jmsTemplate.send(destination, new MessageCreator() {
		      public Message createMessage(Session session) throws JMSException {
		        return session.createTextMessage(msg);
		      }
		    });
	}
	
	/**
	 * 向默认队列发送消息
	 */
	  public void sendMessage(final String msg) {
	    String destination =  jmsTemplate.getDefaultDestination().toString();
	    System.out.println("向队列" +destination+ "发送了消息------------" + msg);
	    jmsTemplate.send(new MessageCreator() {
	      public Message createMessage(Session session) throws JMSException {
	        return session.createTextMessage(msg);
	      }
	    });
	 
	  }

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	 
	
}

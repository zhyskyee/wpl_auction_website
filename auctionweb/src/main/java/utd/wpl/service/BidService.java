package utd.wpl.service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 8, 2018 10:40:06 AM
* 
***********************************************/
@Component
public class BidService {
	
	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {  
        return jmsTemplate;  
    }

	@Resource
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
   
	public void sendBidRequest(Destination destination, final String jsonStr) {
		System.out.println("---Sending a bid request to the queue--->>" + jsonStr);
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(jsonStr);
			}
		});
		
		
	}
}

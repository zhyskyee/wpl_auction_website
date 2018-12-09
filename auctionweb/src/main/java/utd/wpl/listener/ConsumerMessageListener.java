package utd.wpl.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import utd.wpl.pojo.Bid;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 8, 2018 10:49:50 AM
* 
***********************************************/
public class ConsumerMessageListener implements MessageListener{

	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		TextMessage tm = (TextMessage) msg;
		Gson gson = new Gson();
		Bid recvBid;
		try {
			recvBid = gson.fromJson(tm.getText(), Bid.class);
			System.out.println("---Notified a message---");
			System.out.println("Bidder is :"+recvBid.getBidderid()+"  | price is :"+ recvBid.getBidprice());
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

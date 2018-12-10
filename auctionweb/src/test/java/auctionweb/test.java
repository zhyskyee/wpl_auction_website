package auctionweb;

import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.JsonObject;
import utd.wpl.service.BidService;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 8, 2018 11:02:20 AM
* 
***********************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-activemq.xml")
public class test {
	@Autowired
	private BidService bidService;
	@Autowired
	@Qualifier("queueDestination")
	private Destination destination;
	
	@Test
	public void testSend() throws InterruptedException {
		for (int i = 0; i < 5; ++i) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("bidderid", i);
			jsonObject.addProperty("bidprice", 10.1*i);
			bidService.sendBidRequest(destination, jsonObject.toString());
		}
		Thread.sleep(1000);
	}

}

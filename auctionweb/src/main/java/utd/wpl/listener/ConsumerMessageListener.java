package utd.wpl.listener;

import java.io.IOException;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import utd.wpl.pojo.Bid;
import utd.wpl.pojo.Item;
import utd.wpl.pojo.Result;
import utd.wpl.service.BidService;

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
			System.out.println("---Receive a bid message---");
			System.out.println("Itemid is:"+recvBid.getItemid()+" | Bidder is :"+recvBid.getBidderid()+"  | price is :"+ recvBid.getPrice());
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
				/*
				 * 添加参数到URL的尾巴
				 */
				URIBuilder builder = new URIBuilder("http://localhost:8989/item/bid");

				// 第三步：给httpPost设置JSON格式的参数
				StringEntity requestEntity = new StringEntity(tm.getText(), "utf-8");
				HttpPost httpPost = new HttpPost(builder.build());
				requestEntity.setContentEncoding("UTF-8");
				httpPost.setHeader("Content-type", "application/json");
				httpPost.setEntity(requestEntity);
				// 第四步：发送HttpPost请求，获取返回值
				HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				// Response Body
				String responseBody = responseHandler.handleResponse(hr);
				System.out.println("Recive forom B in Conssumer ===> "+responseBody);
				if (responseBody != null && !responseBody.equals("")) {
					System.out.println("Fail bid===>"+responseBody);
				} else {
					System.out.println("Success add a bid record");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			finally {
				try {
					httpClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

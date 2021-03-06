package utd.wpl.listener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.camel.model.validator.ValidatorDefinition;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
	 class JsonDateDeserializer implements JsonDeserializer<Date> {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			// TODO Auto-generated method stub
			String s = json.getAsJsonPrimitive().getAsString();
			long l = Long.parseLong(s);
			return new Date(l);
		}
	}
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
				System.out.println("Recive from B in Consumer ===> "+responseBody);
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
//	private void tryUpdatePrice(double price, int itemid, int newownerid) {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		try {
//			/*
//			 * 添加参数到URL的尾巴
//			 */
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("itemid", itemid);
//			jsonObject.put("bidprice", price);
//			jsonObject.put("ownerid", newownerid);
//			
//			URIBuilder builder = new URIBuilder("http://localhost:8989/item/price");
//			// 第二步：创建httpPost对象
//			HttpPost httpPost = new HttpPost(builder.build());
//			// 第三步：给httpPost设置JSON格式的参数
//			StringEntity requestEntity = new StringEntity(jsonObject.toString(), "utf-8");
//			httpPost.setHeader("Content-type", "application/json");
//			// 第四步：发送HttpPost请求，获取返回值
//			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
//			ResponseHandler<String> responseHandler = new BasicResponseHandler();
//			// Response Body
//			String responseBody1 = responseHandler.handleResponse(hr);
//			if (responseBody1 != null && !responseBody1.equals("")) {
//				Result result = null;
//				Gson gson1 = new Gson();
//				try {
//					result = gson1.fromJson(responseBody1, Result.class);
//					System.out.println("tryUpdatePrice>Response From B:" + result.getAnswer());
//				} catch (JsonSyntaxException ex) {
//					// TODO: handle exception
//					ex.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		finally {
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
}

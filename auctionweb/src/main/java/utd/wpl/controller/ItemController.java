package utd.wpl.controller;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.language.Simple;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.wpl.pojo.Item;
import utd.wpl.pojo.Result;
import utd.wpl.pojo.User;

//import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
//import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
@RequestMapping("/item")
public class ItemController {
	@GetMapping(value = "/timeslots")
	public ResponseEntity<List<Boolean>> getDateTimeSlots(@RequestBody Map map) {
		String datestr = (String) map.get("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			/*
			 * 添加参数到URL的尾巴
			 */
			URIBuilder builder = new URIBuilder("http://localhost:8989/item/timslots");
			builder.addParameter("date", sdf.format(date));
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
//			httpPost.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			if (responseBody != null && !responseBody.equals("")) {
//				Gson gson = new Gson();
				List<Boolean> list = null;
				try {
//					list = gson.fromJson(responseBody, List.class);
					Type listType = new TypeToken<ArrayList<Boolean>>(){}.getType();
					list = new Gson().fromJson(responseBody, listType);
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<List<Boolean>>(list, HttpStatus.OK);
			}
			// int statusCode = hr.getStatusLine().getStatusCode();
			// System.out.println("statusCode:::" + statusCode+" resp:"+responseBody);
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
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/curitem")
	public ResponseEntity<Item> getCurItem() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			/*
			 * 添加参数到URL的尾巴
			 */
			URIBuilder builder = new URIBuilder("http://localhost:8989/item/one");
			builder.addParameter("time", date);
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
//			httpPost.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			if (responseBody != null && !responseBody.equals("")) {
				Gson gson = new Gson();
				Item item;
				try {
					item = gson.fromJson(responseBody, Item.class);
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<Item>(item, HttpStatus.OK);
			}
			// int statusCode = hr.getStatusLine().getStatusCode();
			// System.out.println("statusCode:::" + statusCode+" resp:"+responseBody);
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
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	} 
	// 請求ONEITEM
	@RequestMapping(value = "/one", method = RequestMethod.GET)
	public void getOneItem(@RequestParam("current_time") String currentTime) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet("http://localhost:8989/item/one?current_time=" + currentTime);

			// 第四步：发送HttpPost请求，获取返回值
			String reString = httpClient.execute(httpGet, responseHandler); // 调接口获取返回值时，必须用此方法
			// 。。。。。。。。
			System.out.println("Ressutl:" + reString);

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
	}

	// 請求ALLITEM
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void getAllItem(@RequestParam("start_time") SimpleDateFormat startTime,
			@RequestParam("end_time") SimpleDateFormat endTime) {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet(
					"http://localhost:8989/item/all?start_time=" + startTime + "&end_time=" + endTime);

			// 第四步：发送HttpPost请求，获取返回值
			String reString = httpClient.execute(httpGet, responseHandler); // 调接口获取返回值时，必须用此方法
			// 。。。。。。。。
			System.out.println("Ressutl:" + reString);

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
	}

	// 請求postNewITEM
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public void postNewItem(Item item) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject object = new JSONObject();
		object.put("title", item.getTitle());
		object.put("address", item.getAddress());
		object.put("description", item.getDescription());
		object.put("photo", item.getPhoto());
		object.put("auction_date", item.getAuction_date());
		object.put("min_price", item.getMin_price());
		object.put("deal_price", item.getMin_price());
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/item/new");

			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(object.toString(), "utf-8");

			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);

			// 第四步：发送HttpPost请求，获取返回值
			String reString = httpClient.execute(httpPost, responseHandler); // 调接口获取返回值时，必须用此方法
			// 。。。。。。。。
			System.out.println("Ressutl:" + reString);
			// getitemID and store

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
	}

	// 請求deleteITEM
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteItem(Item item) {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/item/all?itemid=" + item.getItemid());

			// 第四步：发送HttpPost请求，获取返回值
			String reString = httpClient.execute(httpPost, responseHandler); // 调接口获取返回值时，必须用此方法
			// 。。。。。。。。
			System.out.println("Ressutl:" + reString);

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
	}

	// 請求editITEM
	@RequestMapping(value = "/owner" + "", method = RequestMethod.POST)
	public void editItem(Item item) {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/item");

			// 第四步：发送HttpPost请求，获取返回值
			String reString = httpClient.execute(httpPost, responseHandler); // 调接口获取返回值时，必须用此方法
			// 。。。。。。。。
			System.out.println("Ressutl:" + reString);

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
	}
}
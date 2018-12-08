package utd.wpl.controller;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;

import org.apache.camel.language.Simple;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import utd.wpl.controller.UserController.JsonDateDeserializer;
import utd.wpl.pojo.Item;
import utd.wpl.pojo.Result;
import utd.wpl.pojo.User;
import utd.wpl.service.BidService;
//import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
//import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
@RequestMapping("/item")
//@ContextConfiguration("/spring/application-activemq.xml")
public class ItemController {
	
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
	@Autowired
	private BidService bidService;
	@Autowired
	@Qualifier("queueDestination")
	private Destination destination;
	
	@PostMapping(value = "/bid")
	public ResponseEntity<Result> bidForItem(@RequestBody Map map, HttpServletRequest request) {
		User fUser = (User) request.getSession().getAttribute("user");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("itemid", map.get("itemid"));
		jsonObject.put("bidderid", fUser.getUserid());
		jsonObject.put("price", map.get("price"));
		Date date = new Date();
		String objStr = jsonObject.toString();
		Result result = new Result();
		result.setAnswer("fail");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			/*
			 * 添加参数到URL的尾巴
			 */
			URIBuilder builder = new URIBuilder("http://localhost:8989/item/"+map.get("itemid"));
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
			httpGet.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpGet); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			if (responseBody != null && !responseBody.equals("")) {
//				Gson gson = new Gson();
				Item item = null;
				Gson gson1 = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
//				User repUser = null;
				try {
					item = gson1.fromJson(responseBody, Item.class);
					System.out.println(
							"Response From B:" + item.getDescription() + " date:" + item.getAuction_date());
					Date date2 = item.getAuction_date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date2);
					calendar.add(Calendar.MINUTE, 20);
					date2 = calendar.getTime();
					System.out.println("Auction_End_time:"+date2+ "   | request_reive_time:"+ date);
//					if (date2.getTime() < date.getTime()) {
//						return new ResponseEntity<Result>(result, HttpStatus.OK);
//					}
					System.out.println("====>Send a bid<======");
					bidService.sendBidRequest(destination, objStr);
					result.setAnswer("success");
					return new ResponseEntity<Result>(result, HttpStatus.OK);
//					result.setAnswer("Success");
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
			return new ResponseEntity<Result>(result, HttpStatus.OK);
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
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/timeslots")
	public ResponseEntity<List<Result>> getDateTimeSlots(@RequestParam("date") String datestr) {
//		String datestr = (String) map.get("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null) return new ResponseEntity<>(HttpStatus.OK);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			/*
			 * 添加参数到URL的尾巴
			 */
			URIBuilder builder = new URIBuilder("http://localhost:8989/item/timeslots");
			builder.addParameter("date", sdf.format(date));
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
			httpGet.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpGet); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			if (responseBody != null && !responseBody.equals("")) {
//				Gson gson = new Gson();
				List<Result> list = null;
				try {
//					list = gson.fromJson(responseBody, List.class);
					Type listType = new TypeToken<ArrayList<Result>>(){}.getType();
					list = new Gson().fromJson(responseBody, listType);
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<List<Result>>(list, HttpStatus.OK);
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
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/curitem")
	public String getCurItem(HttpServletRequest request) {
//		System.out.println("-----====>"+map.get("date"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		System.out.println("===========>Aserver getcuritem"+date);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			/*
			 * 添加参数到URL的尾巴
			 */
			URIBuilder builder = new URIBuilder("http://localhost:8989/item/one");
			builder.addParameter("time", date);
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
//			httpPost.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpGet); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			
			String responseBody = responseHandler.handleResponse(hr);
			System.out.println("A from B: responseBody:"+responseBody);
			return responseBody;
//			if (responseBody != null && !responseBody.equals("")) {
////				Gson gson = new Gson();
////				Item item;
//				Item item = null;
//				Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
//				try {
//					item = gson.fromJson(responseBody, Item.class);
//				} catch (JsonSyntaxException ex) {
//					// TODO: handle exception
//					ex.printStackTrace();
//					return new ResponseEntity<>(HttpStatus.OK);
//				}
//				return new ResponseEntity<Item>(item, HttpStatus.OK);
//			}
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
//		return new ResponseEntity<>(HttpStatus.OK);
		return null;
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
	
	@RequestMapping(value = "/allpost", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getAllPostItem(HttpServletRequest request) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		User user = (User)request.getSession().getAttribute("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 第二步：创建httpPost对象

			URIBuilder builder = new URIBuilder("http://localhost:8989/item/allpost");
			builder.addParameter("ownerid", String.valueOf(user.getUserid()));
			builder.addParameter("time", sdf.format(new Date()));
			System.out.println("Onere::::"+user.getUserid());
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
			httpGet.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpGet); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			System.out.println("A ser postItems:"+responseBody);

			if (responseBody != null && !responseBody.equals("")) {
//				Gson gson = new Gson();
				Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
				List<Item> list = null;
				try {
					Type listType = new TypeToken<ArrayList<Item>>(){}.getType();
					list = gson.fromJson(responseBody, listType);
//					list = gson.fromJson(responseBody, List.class);
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					return new ResponseEntity<>(HttpStatus.OK);
				}
				return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
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
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

	// 請求ALLITEM
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<Item>> getAllItem(@RequestParam("start_time") String startTime,
			@RequestParam("end_time") String endTime) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 第二步：创建httpPost对象

			URIBuilder builder = new URIBuilder("http://localhost:8989/item/all");
			
			builder.addParameter("start_time", startTime);
			builder.addParameter("end_time", endTime);
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
			httpGet.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpGet); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			if (responseBody != null && !responseBody.equals("")) {
				Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
				List<Item> list = null;
				try {
					Type listType = new TypeToken<ArrayList<Item>>(){}.getType();
					list = gson.fromJson(responseBody, listType);
//					list = gson.fromJson(responseBody, List.class);
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					return new ResponseEntity<>(HttpStatus.OK);
				}
				return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
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
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

	// 請求postNewITEM
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<Result> postNewItem(@RequestBody Map<String, String> map, HttpServletRequest request) { //, @RequestParam("auction_date") String ad
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject object = new JSONObject();
		object.put("title", map.get("title"));
		System.out.println("=====>"+map.get("title"));
		object.put("address", map.get("address"));
		System.out.println("=====>"+map.get("address"));
		object.put("description", map.get("description"));
		System.out.println("=====>"+map.get("description"));
		object.put("photo", map.get("photo"));
		System.out.println("=====>"+map.get("photo"));
//		object.put("auction_date", map.get("auction_date"));
		User owner = (User) request.getSession().getAttribute("user");
		System.out.println("userid:"+owner.getUserid());
		object.put("ownerid", String.valueOf(owner.getUserid()));
		object.put("min_price", map.get("min_price"));
		System.out.println("=====>"+map.get("min_price"));
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ad = map.get("auction_date");
		int index = Integer.parseInt(map.get("indextime"));
		System.out.println("=====>"+map.get("indextime"));
		Date date = null;
		try {
			date = sdf.parse(ad);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, index*20);
		System.out.println("Seclect date:"+calendar.getTime());
		object.put("auction_date", sdf.format(calendar.getTime()));

		try {
			// 第一步：创建HttpClient对象
//			httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/item/new");

			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(object.toString(), "utf-8");

			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);
			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			if (responseBody != null && !responseBody.equals("")) {
				Gson gson = new Gson();
				Result result = null;
				try {
					result = gson.fromJson(responseBody, Result.class);
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
					return new ResponseEntity<>(HttpStatus.OK);
				}
				return new ResponseEntity<Result>(result, HttpStatus.OK);
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
		Result res = new Result();
		res.setAnswer("fail");
		return new ResponseEntity<Result>(res, HttpStatus.OK);
	}

	// 請求deleteITEM
	@DeleteMapping("/{itemid}")
	public ResponseEntity<Result> deleteItem(@PathVariable("itemid") int itemid) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			/*
			 * 添加参数到URL的尾巴
			 */
			URIBuilder builder = new URIBuilder("http://localhost:8989/item/"+itemid);
			// 第二步：创建httpPost对象
			HttpDelete httpDelete = new HttpDelete(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
//			httpPost.setHeader("Content-type", "application/json");
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpDelete); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			if (responseBody != null && !responseBody.equals("")) {
				Gson gson = new Gson();
				Result result = new Result();
				result.setAnswer("fail");
				try {
					result = gson.fromJson(responseBody, Result.class);
				} catch (JsonSyntaxException ex) {
					// TODO: handle exception
					ex.printStackTrace();
//					return new ResponseEntity<>(HttpStatus.OK);
				}
				return new ResponseEntity<Result>(result, HttpStatus.OK);
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
		return new ResponseEntity<>(HttpStatus.OK);
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

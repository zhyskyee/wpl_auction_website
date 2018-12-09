package utd.wpl.controller;

import com.google.gson.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.wpl.pojo.Result;
import utd.wpl.pojo.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

@RestController
@RequestMapping("/user")
public class UserController {
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

	@PostMapping("/forgetpassword")
	public ResponseEntity<Result> forgetPswd(User user) {
		System.out.println("username" + user.getUsername());
		System.out.println("password" + user.getPassword());

		JSONObject object = new JSONObject();
		object.put("username", user.getUsername());
		object.put("password", user.getPassword());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		Result result = new Result();
		result.setAnswer("fail");
		try {
			// 第一步：创建HttpClient对象
			// httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/user/resetpswd");

			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(object.toString(), "utf-8");

			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);

			// 第四步：发送HttpPost请求，获取返回值
			// HttpResponse hr = httpClient.execute(httpPost); // 调接口获取返回值时，必须用此方法
			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			System.out.println("RESULT:" + responseBody);
			Gson gson = new Gson();
			try {
				result = gson.fromJson(responseBody, Result.class);
				// System.out.println("REspones From B:"+ result.getAnswer());
			} catch (JsonSyntaxException ex) {
				// TODO: handle exception
				ex.printStackTrace();
				result.setAnswer("fail");
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
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
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/profile")
	public ResponseEntity<User> showProfile(RequestEntity<User> entity) {
		User tmp = entity.getBody();
		System.out.println("Username:" + tmp.getUsername() + "    Pswd:" + tmp.getPassword());

		CloseableHttpClient httpClient = HttpClients.createDefault();

		// ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			// httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet("http://localhost:8989/user/" + tmp.getUsername());
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpGet); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			System.out.println("Result:" + responseBody);
			Gson gson = new Gson();
			User repUser = null;
			try {
				repUser = gson.fromJson(responseBody, User.class);
				System.out.println("Response From B:" + repUser.getUsername());
				repUser.setPassword(null);
			} catch (JsonSyntaxException ex) {
				// TODO: handle exception
				ex.printStackTrace();
				// return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<User>(repUser, HttpStatus.OK);

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

	// 处理登录请求
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Result> login(RequestEntity<User> entity, HttpServletRequest request) {
		User tmp = entity.getBody();
		System.out.println("Username:" + tmp.getUsername() + "    Pswd:" + tmp.getPassword());
		JSONObject object = new JSONObject();
		object.put("username", tmp.getUsername());
		object.put("password", tmp.getPassword());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 第一步：创建HttpClient对象
			// httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/user/login");

			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(object.toString(), "utf-8");

			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);

			// 第四步：发送HttpPost请求，获取返回值
			// HttpResponse hr = httpClient.execute(httpPost); // 调接口获取返回值时，必须用此方法
			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			System.out.println("RESULT:" + responseBody);
			Gson gson = new Gson();
			Result result = null;
			try {
				result = gson.fromJson(responseBody, Result.class);
				// System.out.println("REspones From B:"+ result.getAnswer());
			} catch (JsonSyntaxException ex) {
				// TODO: handle exception
				ex.printStackTrace();
				// return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if (result != null && result.getAnswer().equals("Success")) {
				// ResponseHandler<String> responseHandler = new BasicResponseHandler();
				result.setAnswer("Fail");
				try {
					HttpGet httpGet = new HttpGet("http://localhost:8989/user/" + tmp.getUsername());
					// 第四步：发送HttpPost请求，获取返回值
					HttpResponse hr1 = httpClient.execute(httpGet); // responseHandler调接口获取返回值时，必须用此方法
					ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
					// Response Body
					String responseBody1 = responseHandler1.handleResponse(hr1);
					System.out.println("Result:" + responseBody1);
					// Gson gson1 = new Gson();
					// Gson gson1 = new GsonBuilder().setDateFormat("yyyy-MM-dd
					// HH:mm:ss.S").create();

					Gson gson1 = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
					User repUser = null;
					try {
						repUser = gson1.fromJson(responseBody1, User.class);
						System.out.println(
								"Response From B:" + repUser.getUsername() + " date:" + repUser.getLast_visit());
						request.getSession().setAttribute("user", repUser);

						result.setAnswer("Success");
					} catch (JsonSyntaxException ex) {
						// TODO: handle exception
						ex.printStackTrace();
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

		Result r = new Result();
		r.setAnswer("Fail");
		return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
	}

	// 用户注册
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Result> register(RequestEntity<User> entity, @RequestParam("comfirmPass") String confirmPass) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		User user = entity.getBody();
		JSONObject object = new JSONObject();
		object.put("username", user.getUsername());
		object.put("password", user.getPassword());
		object.put("email", user.getEmail());
		object.put("phone", user.getPhone());
//		object.put("photo", user.getPhoto());
		try {
			// 第一步：创建HttpClient对象
			// httpClient = HttpClients.createDefault();
			/*
			 * 添加参数到URL的尾巴
			 */
			URIBuilder builder = new URIBuilder("http://localhost:8989/user/register");
			builder.addParameter("confirmPass", confirmPass);
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost(builder.build());
			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(object.toString());
			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);
			// 第四步：发送HttpPost请求，获取返回值
			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			int statusCode = hr.getStatusLine().getStatusCode();
			System.out.println("statusCode:::" + statusCode + " resp:" + responseBody);
			Gson gson = new Gson();
			Result result = null;
			try {
				result = gson.fromJson(responseBody, Result.class);
			} catch (JsonSyntaxException ex) {
				// TODO: handle exception
				ex.printStackTrace();
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
		Result res = new Result();
		res.setAnswer("Fail");
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}

	// 用户注销
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			request.getSession().removeAttribute("user");
		}
		return "redirect:/";
	}
}

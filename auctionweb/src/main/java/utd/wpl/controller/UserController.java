package utd.wpl.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import utd.wpl.pojo.User;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
//import com.fasterxml.jackson.databind.util.JSONPObject;

@Controller
public class UserController {
		
		class Result {
		String answer;
		public String getAnswer() {
			return answer;
		}
		public void setAnswer(String answer) {
			this.answer = answer;
		}
		public Result() {
			// TODO Auto-generated constructor stub
			super();
		}
	}


	// 显示主页
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/profile") 
	public void showProfile(User user) {
	
		HttpRequest httpRequest = HttpRequest.get("http://localhost:8989/user/{username}", null, Boolean.TRUE);
		String result = httpRequest.body();
		
		System.out.println(result);

	}
	
	//for now
	@GetMapping("/order") 
	public String showOrder() {
		return "order";
	} 
	//for now
	@GetMapping("/sell") 
	public String showSell() {
		return "sell";
	} 
	// for now
	@GetMapping("/contact") 
	public String showContact() {
		return "contact";
	} 
	
	// 显示注册页面
	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	
	// 处理登录请求
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public void login(@RequestParam("username") String un, @RequestParam("password") String pswd) {
		JSONObject object = new JSONObject();
		object.put("username", un);
		object.put("password", pswd);
		CloseableHttpClient httpClient = HttpClients.createDefault();

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/user/login");

			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(object.toString(), "utf-8");

			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);

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
	
	

	// 用户注册
	@RequestMapping(value = "/register/do", method = RequestMethod.POST)
	public ResponseEntity<Result> register(User user,String confirmPass) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject object = new JSONObject();
		object.put("username", user.getUsername());
		object.put("password", user.getPassword());
		object.put("email", user.getEmail());
		object.put("phone", user.getPhone());
		object.put("photo", user.getPhoto());
//		System.out.println("================>");
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
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
//			System.out.println("statusCode:::" + statusCode+"     resp:"+responseBody);
			
			Gson gson = new Gson();
			Result result;
			try {
				result = gson.fromJson(responseBody, Result.class);
			} catch (JsonSyntaxException ex) {
				// TODO: handle exception
				ex.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
//			result.setAnswer();
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
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	//用户注销
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") != null) {
			request.getSession().removeAttribute("user");
		}
		return "redirect:/";
	}
	
	
/*	
	//回显登录数据
	public void returnLoginData(Model model,User user,String meg) {
		model.addAttribute("username",user.getUsername());
		model.addAttribute("password",user.getPassword());
		model.addAttribute("message",meg);
	}
	
	//回显注册数据
	public void returnRegisterData(Model model,User user,String confirmPass,String meg) {
		model.addAttribute("username",user.getUsername());
		model.addAttribute("password",user.getPassword());
		model.addAttribute("confirmPass",confirmPass);
		model.addAttribute("message",meg);
	}

*/
}


package utd.wpl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.ServletContext;
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
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import utd.wpl.Utils.ImageUtils;
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
	 
	 class JsonByteDeserializer implements JsonDeserializer<byte[]> {
		 	String username;
			@Override
			public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				// TODO Auto-generated method stub
				String s = json.getAsJsonPrimitive().getAsString();
				String file_path = "images/"+username+"_img"+".jpg";
				if (ImageUtils.baseStrToImg(s.getBytes(), file_path)) {
					return file_path.getBytes();
				}
				return null;
			}
			public String getUser() {
				return username;
			}
			public void setUser(String suser) {
				username = suser;
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
				return new ResponseEntity<>(result, HttpStatus.OK);
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
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/profile")
	public ResponseEntity<Result> updateProfile(RequestEntity<User> entity) {
		User tmp = entity.getBody();
		System.out.println("Username:" + tmp.getUsername());
		JSONObject object = new JSONObject();
		object.put("username", tmp.getUsername());
		object.put("email", tmp.getEmail());
		object.put("phone", tmp.getPhone());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		Result result = new Result();
		result.setAnswer("fail");
		// ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			// httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost("http://localhost:8989/user/profile");
			// 第四步：发送HttpPost请求，获取返回值
			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(object.toString(), "utf-8");

			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);

			HttpResponse hr = httpClient.execute(httpPost); // responseHandler调接口获取返回值时，必须用此方法
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Response Body
			String responseBody = responseHandler.handleResponse(hr);
			System.out.println("Result:" + responseBody);
			Gson gson = new Gson();
			
			try {
				result = gson.fromJson(responseBody, Result.class);
				System.out.println("Response From B:"+responseBody);
				
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
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping("/profile")
	public ResponseEntity<User> showProfile(@RequestParam("username") String username) {
//		User tmp = entity.getBody();
		System.out.println("Username:" + username);

		CloseableHttpClient httpClient = HttpClients.createDefault();

		// ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			// httpClient = HttpClients.createDefault();
			// 第二步：创建httpPost对象
			HttpGet httpGet = new HttpGet("http://localhost:8989/user/"+username);
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
//				String imgPathStr = "/images/"+repUser.getUsername()+"_photo.jpg";
//				if (ImageUtils.baseStrToImg(repUser.getPhoto(), imgPathStr)) {
//					//保存图片成功！
//					repUser.setPhoto(imgPathStr.getBytes());
//				}
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
	 public final static boolean isJSONValid2(String jsonInString ) {
	        try {
	            final ObjectMapper mapper = new ObjectMapper();
	            mapper.readTree(jsonInString);
	            return true;
	        } catch (IOException e) {
	            return false;
	        }
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
//					JsonByteDeserializer jb = new JsonByteDeserializer();
					
//					jb.setUser(tmp.getUsername());
//					Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
//					int index = responseBody.indexOf("\"photo\":\"");
//					index += 8;
					
					User repUser = new User();
					if (isJSONValid2(responseBody1)) {
						JSONObject jsonObject = new JSONObject(responseBody1);
						repUser.setUsername(jsonObject.getString("username"));
						repUser.setEmail(jsonObject.getString("email"));
						long l = jsonObject.getLong("last_visit");
						repUser.setLast_visit(new Date(l));
						repUser.setPassword(jsonObject.getString("password"));
						repUser.setPhone(jsonObject.getString("phone"));
						repUser.setUserid(jsonObject.getInt("userid"));
						String img = jsonObject.getString("photo");
						System.out.println(">>>>>>>"+img);
//						String file_path = "/images/"+repUser.getUsername()+"_img.jpg";
//						System.out.println("==>"+file_path);
//						if (ImageUtils.baseStrToImg(img.getBytes(), file_path)) {
						repUser.setPhoto(img.getBytes());
//						}
						System.out.println(
								"Response From B:" + repUser.getUsername() + " date:" + repUser.getLast_visit());
						
						request.getSession().setAttribute("user", repUser);
						result.setAnswer("Success");
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
		return new ResponseEntity<>(r, HttpStatus.OK);
	}
	
	  @RequestMapping(value = "/testpic", method = RequestMethod.POST)
	    public ResponseEntity<Result> testpic(HttpServletRequest request, User user, @RequestPart("pic") MultipartFile avator) { //@RequestParam("test") String test
	        //MultipartFile 接口有很多方法，这只是其中一个。
		  Result result = new Result();
		  result.setAnswer("laalal");
		  System.out.println("=======>"+ user.getUsername());
	        try {
	        System.out.println(request.getServletPath());	
	      //上传文件路径
	        String fileName = avator.getOriginalFilename();
			System.out.println("原始文件名:" + fileName);
	 
			// 新文件名
			String newFileName = UUID.randomUUID() + fileName;
//            String path = request.getServletContext().getRealPath("/images/");
	        ServletContext sc = request.getSession().getServletContext();
	        // 上传位置
			String path = sc.getRealPath("/images") + "/"; // 设定文件保存的目录
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			if (!path.isEmpty()) {
				try {
					FileOutputStream fos = new FileOutputStream(path + newFileName);
					InputStream in = avator.getInputStream();
					int b = 0;
					while ((b = in.read()) != -1) {
						fos.write(b);
					}
					fos.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	 
			System.out.println("上传图片到:" + path + newFileName);
			
            //上传文件名
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return new ResponseEntity<Result>(result, HttpStatus.OK);
	    }

	// 用户注册
//	@Bean(name = "multipartResolver")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Result> register(HttpServletRequest request, String confirmPass ,User user, @RequestPart("pic") MultipartFile avator) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		System.out.println("Leng====>"+user.getPhoto().length);
		JSONObject object = new JSONObject();
		object.put("username", user.getUsername());
		object.put("password", user.getPassword());
		object.put("email", user.getEmail());
		object.put("phone", user.getPhone());
	        System.out.println(request.getServletPath());	
	      //上传文件路径
	        String fileName = avator.getOriginalFilename();
			System.out.println("原始文件名:" + fileName);
	 
			// 新文件名
			String newFileName = user.getUsername()+ "_" + fileName;
//            String path = request.getServletContext().getRealPath("/images/");
	        ServletContext sc = request.getSession().getServletContext();
	        // 上传位置
			String path = sc.getRealPath("/images") + "/"; // 设定文件保存的目录
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			if (!path.isEmpty()) {
				try {
					FileOutputStream fos = new FileOutputStream(path + newFileName);
					InputStream in = avator.getInputStream();
					int b = 0;
					while ((b = in.read()) != -1) {
						fos.write(b);
					}
					fos.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			object.put("photo", (path + newFileName).getBytes());
	 
		
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
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// 用户注销
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			request.getSession().removeAttribute("user");
		}
		return "redirect";
	}
}

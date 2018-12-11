package utd.wpl.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import utd.wpl.pojo.Result;
import utd.wpl.pojo.User;
import utd.wpl.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/resetpswd")
	public ResponseEntity<Result> resetPassword(RequestEntity<User> entity) {
		User user = entity.getBody();
		Result result = new Result();
		result.setAnswer("fail");
		
		if (user.getPassword() != null && user.getPassword() != null) {
			User findUser = userService.findUser(user.getUsername());
			String passwordMD5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			if (findUser != null) {
				if (userService.updatePassword(findUser.getUsername(), passwordMD5) == 1) {
					result.setAnswer("Success");
				} else {
					result.setAnswer("Fail");
				}
			}
		}
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	// 处理登录请求
	@GetMapping(value = "/{username}")
	public ResponseEntity<User> userProfile(@PathVariable("username") String un) {
		User findUser = userService.findUser(un);
		if (findUser == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(findUser, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<Result> login(RequestEntity<User> re) { //@RequestParam("username") String un, @RequestParam("password") String pswd
		User tmp = re.getBody();
		User findUser = userService.findUser(tmp.getUsername());
		
		String passwordMD5 = DigestUtils.md5DigestAsHex(tmp.getPassword().getBytes());
		Result result = new Result();
		if (findUser == null) {
			result.setAnswer("User does not exist");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		if (!passwordMD5.equals(findUser.getPassword())) {
			result.setAnswer("Password is incorrect");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		findUser.setLast_visit(new Date());
		if (userService.updateLastVisit(findUser) == 1) {
			result.setAnswer("Success");
		} else {
			result.setAnswer("Fail");
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 用户注册
	@PostMapping(value = "/register")
	public ResponseEntity<Result> register(RequestEntity<User> requestEntity, @RequestParam("confirmPass") String cfn) throws IOException{
		// 查询用户是否存在
		User user = requestEntity.getBody();
		User findUser = userService.findUser(user.getUsername());
		Result result = new Result();
		if (findUser != null) {
			result.setAnswer("User already exists");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		if (!cfn.equals(user.getPassword())) {
			result.setAnswer("Password does not match");
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		user.setLast_visit(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		if (userService.addUser(user) == 1) {
			result.setAnswer("Success");
		} else {
			result.setAnswer("Fail");
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/profile")
	public ResponseEntity<Result> updateProfile(RequestEntity<User> requestEntity) {
		User findUser = requestEntity.getBody();
		Result result = new Result();
		result.setAnswer("fail");
		if (userService.updateProfile(findUser.getUsername(), findUser.getPhone(), findUser.getEmail()) == 1) {
			result.setAnswer("success");
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

package utd.wpl.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import utd.wpl.pojo.User;
import utd.wpl.service.UserService;
import com.mysql.jdbc.StringUtils;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	// 显示主页
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/profile") 
	public String showProfile() {
		return "profile";
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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(User user, Model model, HttpServletRequest request) {
		//查询用户是否存在
		User findUser = userService.findUser(user.getUsername());
		
		//把密码转成MD5加密后的
		String passwdMD5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		if (findUser == null || !passwdMD5.equals(findUser.getPassword())) {
			// 登录失败
			returnLoginData(model, user,"Username does not existed or password incorrect!");
			return "index";
		}
		//登录成功
		//更新用户最后访问时间
		findUser.setLast_visit(new Date());
		userService.updateLastVisit(findUser);
		//设置username属性，方便jsp页面获取
		model.addAttribute("username", findUser.getUsername());
		model.addAttribute("message", "Login successfully!");
		// 设置用户信息保存到session
		request.getSession().setAttribute("user", findUser);
		//返回主页
		return "index";
	}

	// 用户注册
	@RequestMapping(value = "/register/do", method = RequestMethod.POST)
	public String register(User user, String confirmPass, Model model) {
		// 查询用户是否存在
		User findUser = userService.findUser(user.getUsername());
		if (findUser != null) {
			returnRegisterData(model, user, confirmPass, "用户名已被注册！");
			return "register";
		}
		model.addAttribute("username", user.getUsername());
		//补全pojo属性
		user.setLast_visit(new Date());
		//密码使用MD5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		//添加新用户
		userService.addUser(user);
		return "success";
	}
	
	//用户注销
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") != null) {
			request.getSession().removeAttribute("user");
		}
		return "redirect:/";
	}
	
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
}

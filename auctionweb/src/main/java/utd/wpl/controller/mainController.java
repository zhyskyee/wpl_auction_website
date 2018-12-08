package utd.wpl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 7, 2018 9:12:22 PM
* 
***********************************************/
@Controller
public class mainController {
		// 显示主页
		@RequestMapping("/")
		public String index() {
			return "index";
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
}

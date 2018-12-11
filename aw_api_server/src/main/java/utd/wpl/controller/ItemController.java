package utd.wpl.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.wpl.pojo.Result;
import utd.wpl.pojo.Bid;
import utd.wpl.pojo.Item;
import utd.wpl.service.ItemService;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 6, 2018 11:24:29 PM
* 
***********************************************/
@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemByID(@PathVariable("id") int itemid) {
		Item item = itemService.findItemByItemId(itemid);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/timeslots")
	public ResponseEntity<List<Result>> getAvailableTimeSlots(@RequestParam("date") String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("B server===>"+date);
		Date date_cutime;
		try {
			date_cutime = sdf.parse(date);
			System.out.println(date_cutime); // prints 10-04-2018
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
		}
		List<Result> slots = itemService.getAvaTimeSlotsByDate(date_cutime);
		if (slots == null)
			return new ResponseEntity<>(HttpStatus.OK);
		
		return new ResponseEntity<List<Result>>(slots, HttpStatus.OK);
	}
	@GetMapping(value = "/one")
	public ResponseEntity<Item> getCurBidItem(@RequestParam("time") String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date_cutime;
		try {
			date_cutime = sdf.parse(time);
			System.out.println("Bserver_getcurItem:"+date_cutime); // prints 10-04-2018
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
		}
		Item item = itemService.findCurItem(date_cutime);
		if (item == null)
			return new ResponseEntity<Item>(HttpStatus.OK);
		
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Item>> getAllBidItem(@RequestParam("start_time") String st, @RequestParam("end_time") String et) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date_st, date_et;
		try {
			date_st = sdf.parse(st);
			date_et = sdf.parse(et);
//			System.out.println(date_st+" --- "+date_et); // prints 10-04-2018
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
		}
		List<Item> list = itemService.findAllItems(date_st, date_et);
		if (list == null) {
			return new ResponseEntity<List<Item>>(HttpStatus.OK);
		}
		return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/allpost")
	public ResponseEntity<List<Item>> getAllPostItem(@RequestParam("ownerid") int ownerid, @RequestParam("time") String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(time);
//			System.out.println(date_st+" --- "+date_et); // prints 10-04-2018
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
		}
		List<Item> list = itemService.findAllPostItems(ownerid, date);
		if (list == null) {
			System.out.println("B ser postItems:NULL");

			return new ResponseEntity<List<Item>>(HttpStatus.OK);
		}
		return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
	}
	
	@PostMapping(value = "/new")
	public ResponseEntity<Result> postNewItem(@RequestBody Map<String, String> map) {
		Item newItem = new Item();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		newItem.setTitle(map.get("title"));
		System.out.println("-------->"+map.get("title"));
		newItem.setAddress(map.get("address"));
		System.out.println("-------->"+map.get("address"));
		try {
			newItem.setAuction_date(sdf.parse(map.get("auction_date")));
			System.out.println("-------->"+map.get("auction_date"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newItem.setDescription(map.get("description"));
		System.out.println("-------->"+map.get("description"));
		newItem.setMin_price(Double.parseDouble(map.get("min_price")));
		System.out.println("-------->"+map.get("min_price"));
		newItem.setOwnerid(Integer.parseInt(map.get("ownerid")));
		System.out.println("-------->"+map.get("ownerid"));
		
		Item findItem = itemService.findItemByItemAddress(newItem.getAddress());
		Result result = new Result();
		if (findItem != null) {
			result.setAnswer("Item already exists");
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
		
		if (itemService.addItem(newItem) == 0) {
			result.setAnswer("Fails to addItem for unknown");
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
		result.setAnswer("success");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{itemid}")
	public ResponseEntity<Result> deleteItem(@PathVariable("itemid") int itemid) {
		Result result = new Result();
		result.setAnswer("success");
		if (itemService.deleteItemByItemId(itemid) == 0) {
			result.setAnswer("Fails to deleteItem for unknown");
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
		return new ResponseEntity<Result>(HttpStatus.OK);
	}
	
//	@PostMapping(value = "/owner")
	public void updateOwner(Bid bid) {
		if (itemService.updateItemOwner(Integer.valueOf(bid.getItemid()), Integer.valueOf(bid.getBidderid())) == 0) {
			System.out.println("Fatal: fail to update owner!!!!");
		}
	}
	
//	@PostMapping(value = "/price")
	public void updateAuctionPrice(Bid bid) {
		int itemid = bid.getItemid();
		double bidprice = bid.getPrice();

		Item item = itemService.findItemByItemId(itemid);
		if (item == null) {
			System.out.println("Item["+itemid+"] not in server");
			return;
		}
		if (bidprice <= item.getDeal_Price()) {
			System.out.println("The bidding price is lower");
			return;
		}
		if (itemService.updateItemDealPrice(itemid, bidprice) == 0) {
			System.out.println("Fails to updateItemDealPrice for unknown");
			return;
		}
		updateOwner(bid);
	}
	
	@PostMapping(value = "/bid")
	public ResponseEntity<Result> addBidRecord(RequestEntity<Bid> entity) {
		Bid bb = entity.getBody();
//		System.out.println("");
		Result result = new Result();
		if (itemService.addBidRecord(bb) == 0) {
			result.setAnswer("Fails to addBidRecord for unknown");
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		}
		updateAuctionPrice(bb);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/bid")
	public ResponseEntity<List<Bid>> getBidRecords(@RequestParam("itemid") String itemids, @RequestParam(value = "ownerid", required = false) String ownerids, @RequestParam(value = "bidderid", required = false) String bidderids) {//
//		Integer itemid = Integer.parseInt(itemids);
//		Integer ownerid = Integer.parseInt(ownerids);
//		Integer bidderid = Integer.parseInt(bidderids);
		List<Bid> list = itemService.getBidRecords(itemids, ownerids, bidderids); //
		return new ResponseEntity<List<Bid>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/setime")
	public ResponseEntity<Result> resetItemTime(@RequestParam Map<String, String> map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("-------->"+map.get("itemid"));
		int itemid = Integer.parseInt(map.get("itemid"));
		Date date = null;
		Result result = new Result();
		result.setAnswer("fail");
		try {
			date = sdf.parse(map.get("auction_date"));
			System.out.println("-------->"+date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (itemService.updateItemAuctionDate(itemid, date) == 1) {
			result.setAnswer("success");
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

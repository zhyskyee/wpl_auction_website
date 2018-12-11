package utd.wpl.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalin.MemCached.MemCachedClient;

import utd.wpl.dao.BidDao;
import utd.wpl.dao.ItemDao;
import utd.wpl.pojo.Bid;
import utd.wpl.pojo.Item;
import utd.wpl.pojo.Result;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 6, 2018 10:47:37 PM
* 
***********************************************/
@Service
public class ItemService {
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private BidDao bidDao;
	
	@Autowired
	private MemCachedClient memCachedClient;
	
//	private final static String memCurItem = "curItem";
	private final static String memPrefix = "itemid";
	private final static int auction_gap = 20; // 20 min
	public int updateItemAuctionDate(int itemid, Date date) {
		return itemDao.updateItemAuctionDate(itemid, date);
	}
	
	public int updateItemDealPrice(int itemid, double deal_price) {
		Item item = this.findItemByItemId(itemid);
		item.setDeal_Price(deal_price);
		memCachedClient.set(memPrefix+itemid, item);
		return itemDao.updateItemDealPrice(itemid, deal_price);
	}
	public int updateItemOwner(int itemid, int newOwnerId) {
		Item item = (Item) memCachedClient.get(memPrefix+itemid);
		if (item != null) {
			item.setOwnerid(newOwnerId);
		}
		return itemDao.updateItemOwner(itemid, newOwnerId);
	}
	
	public List<Item> findAllItems(Date start_time, Date end_time) {
		return itemDao.findAllItems(start_time, end_time);
	}
	
	public  List<Item> findAllPostItems(int ownerid, Date time) {
		return itemDao.findAllPostItems(ownerid, time);
	}
	
	public List<Result> getAvaTimeSlotsByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		Date et = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		Date st = calendar.getTime();
		List<Item> list = itemDao.findAllItems(st, et);
		Set<Date> set = new HashSet<>();
		for (Item item : list) {
			set.add(item.getAuction_date());
		}
		Boolean[] re = new Boolean[24];
		List<Result> listResult = new ArrayList<>();
		Date tmp = new Date(st.getTime());
		for (int i = 0; i < re.length; i++) {
			Result tmp1 = new Result();
			if (set.contains(tmp)) {
				tmp1.setAnswer("false");
			} else {
				tmp1.setAnswer("true");
			}
			listResult.add(tmp1);
			calendar.add(Calendar.MINUTE, auction_gap);
			tmp = calendar.getTime();
		}
		return listResult;
	}
	
	public Item findCurItem(Date cur_time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cur_time);
		calendar.set(Calendar.SECOND, 0);
		int miniute = calendar.get(Calendar.MINUTE);
		miniute -= miniute % auction_gap; //20分钟
		calendar.set(Calendar.MINUTE, miniute);
		Date conversion = calendar.getTime();
		System.out.println("Request to retireve cur bid item:"+ conversion);
		Item newItem = itemDao.findCurItem(conversion);
		if (newItem != null) {
			memCachedClient.set(memPrefix+newItem.getItemid(), newItem);
		}
		
		return newItem;
	}
	public int addItem(Item item) {
		return itemDao.addItem(item);
	}
	public Item findItemByItemId(int itemid) {
		Item item = (Item) memCachedClient.get(memPrefix+itemid);
		if (item != null) {
			return item;
		}
		item = itemDao.findItemByItemId(itemid);
		if (item != null) {	//still no this item 	
			memCachedClient.set(memPrefix+itemid, item);
		} 
		return item;
	}
	public int deleteItemByItemId(int itemid) {
		Item delItem = (Item) memCachedClient.get(memPrefix+itemid);
		if (delItem != null) {
			memCachedClient.delete(memPrefix+itemid);
		}
		return itemDao.deleteItemByItemId(itemid);
	}
	public Item findItemByItemAddress(String address) {
		return itemDao.findItemByItemAddress(address);
	}
	//bid: operations;
	public int addBidRecord(Bid bid) {
		return bidDao.addBidRecord(bid);
	}
	public List<Bid> getBidRecords(String itemid, String ownerid, String bidderid) {
		return bidDao.getBidRecordsByIds(itemid, ownerid, bidderid);
	}
}

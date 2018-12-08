package utd.wpl.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalin.MemCached.MemCachedClient;

import utd.wpl.dao.BidDao;
import utd.wpl.dao.ItemDao;
import utd.wpl.pojo.Bid;
import utd.wpl.pojo.Item;

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
	public void updateItemDealPrice(int itemid, double deal_price) {
		Item item = this.findItemByItemId(itemid);
		item.setDeal_Price(deal_price);
		memCachedClient.set(memPrefix+itemid, item);
		itemDao.updateItemDealPrice(itemid, deal_price);
	}
	public void updateItemOwner(int itemid, int newOwnerId) {
		Item item = (Item) memCachedClient.get(memPrefix+itemid);
		if (item != null) {
			item.setOwnerid(newOwnerId);
		}
		itemDao.updateItemOwner(itemid, newOwnerId);
	}
	
	public List<Item> findAllItems(Date start_time, Date end_time) {
		return itemDao.findAllItems(start_time, end_time);
	}
	
	public Item findCurItem(Date cur_time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(cur_time);
		calendar.set(Calendar.SECOND, 0);
		int miniute = calendar.get(Calendar.MINUTE);
		miniute -= miniute % 10; //10分钟
		calendar.set(Calendar.MINUTE, miniute);
		Date conversion = calendar.getTime();
		System.out.println("Request to retireve cur bid item:"+ conversion);
		Item newItem = itemDao.findCurItem(conversion);
		if (newItem != null) {
			memCachedClient.set(memPrefix+newItem.getItemid(), newItem);
		}
		
		return newItem;
	}
	public void addItem(Item item) {
		itemDao.addItem(item);
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
	public void deleteItemByItemId(int itemid) {
		Item delItem = (Item) memCachedClient.get(memPrefix+itemid);
		if (delItem != null) {
			memCachedClient.delete(memPrefix+itemid);
		}
		itemDao.deleteItemByItemId(itemid);
	}
	
	//bid: operations;
	public void addBidRecord(Bid bid) {
		bidDao.addBidRecord(bid);
	}
	public List<Bid> getBidRecords(Integer itemid, Integer ownerid, Integer bidderid) {
		return bidDao.getBidRecordsByIds(itemid, ownerid, bidderid);
	}
}

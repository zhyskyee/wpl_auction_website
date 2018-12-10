package utd.wpl.dao;
/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 5, 2018 4:20:09 PM
* 
***********************************************/

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import utd.wpl.pojo.Item;

public interface ItemDao {
	int updateItemOwner(@Param("itemid") int itemid, @Param("ownerid") int newOwnerId);
	int updateItemDealPrice(@Param("itemid") int itemid, @Param("deal_price") double deal_price);
	List<Item> findAllItems(@Param("start_time") Date start_time, @Param("end_time") Date end_time);
	Item findCurItem(@Param("start_time") Date cur_time);
	int addItem(@Param("item") Item item);
	Item findItemByItemAddress(@Param("address") String address);
	Item findItemByItemId(@Param("itemid") int itemid);
	int deleteItemByItemId(@Param("itemid") int itemid);
}

package utd.wpl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import utd.wpl.pojo.Bid;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 7, 2018 1:34:00 AM
* 
***********************************************/
public interface BidDao {
	int addBidRecord(@Param("bid") Bid bid);
	List<Bid> getBidRecordsByIds(@Param("itemid") String itemid, @Param(value ="ownerid") String ownerid, @Param("bidderid") String bidderid);
}

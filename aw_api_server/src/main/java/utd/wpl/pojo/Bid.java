package utd.wpl.pojo;

import java.io.Serializable;
import java.util.Date;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 7, 2018 1:21:36 AM
* 
***********************************************/
public class Bid implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1575883992292423029L;
	
	public Bid() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	private int itemid;
	private int ownerid;
	private int bidderid;
	private int bidprice;
	private Date timestamp;

	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public int getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}
	public int getBidderid() {
		return bidderid;
	}
	public void setBidderid(int bidderid) {
		this.bidderid = bidderid;
	}
	public int getBidprice() {
		return bidprice;
	}
	public void setBidprice(int bidprice) {
		this.bidprice = bidprice;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}

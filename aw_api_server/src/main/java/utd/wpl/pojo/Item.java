package utd.wpl.pojo;

import java.io.Serializable;
import java.util.Date;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 5, 2018 4:15:41 PM
* 
***********************************************/
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8655641440995333161L;
	private int itemid;
	private String title;
	private int ownerid;
	private String address;
	private String description;
	private byte[] photo;
	private Date auction_date;
	private double min_price;
	private double deal_price;
	
	public Item() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String itemname) {
		this.title = itemname;
	}
	public int getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public Date getAuction_date() {
		return auction_date;
	}
	public void setAuction_date(Date auction_date) {
		this.auction_date = auction_date;
	}
	public double getDeal_Price() {
		return deal_price;
	}
	public void setDeal_Price(double price) {
		this.deal_price = price;
	}
	public double getMin_price() {
		return min_price;
	}
	public void setMin_price(double min_price) {
		this.min_price = min_price;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	
}

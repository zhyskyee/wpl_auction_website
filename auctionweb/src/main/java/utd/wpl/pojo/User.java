package utd.wpl.pojo;

import java.io.Serializable;
import java.util.Date;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Nov 16, 2018 2:50:19 PM
* 
***********************************************/
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6031856798590779968L;
	private int id;
	private String username;
	private String password;
	private Date last_visit;
	private String email;
	private String phone;
	private byte[] photo;

	public User() {
		super();	
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getLast_visit() {
		return last_visit;
	}
	public void setLast_visit(Date last_visit) {
		this.last_visit = last_visit;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}

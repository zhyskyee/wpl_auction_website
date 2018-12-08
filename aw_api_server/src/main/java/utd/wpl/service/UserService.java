package utd.wpl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whalin.MemCached.MemCachedClient;

import utd.wpl.dao.UserDao;
import utd.wpl.pojo.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private MemCachedClient memCachedClient;
	
//	public MemCachedClient getMemCachedClient() {
//		return this.memCachedClient;
//	}
//	
//	
//	public void setMemCachedClient(MemCachedClient memCachedClient) {
//		this.memCachedClient = memCachedClient;
//	}
	
	/**
	 * 查询用户信息
	 * @param username 用户名
	 * @return
	 */
	public User findUser(String username) {
		User curUser;
		if ((curUser = (User) memCachedClient.get(username)) == null) {
			System.out.println("Do not hit the "+username+"'s profile!!!"+ "  GO TO Database!!!!");
			curUser = userDao.findUserByUserName(username);
			if (curUser != null)  {
				memCachedClient.set(curUser.getUsername(), curUser);
			}
		} else {
			System.out.println(username + " Hit the memcache!!! "+ "return it");
		}
		return curUser;
	}
	
	/**
	 * 添加新用户
	 * @param user
	 */
	public void addUser(User user) {
		userDao.insertUser(user);
		memCachedClient.set(user.getUsername(), user);
	}
	
	/**
	 * 更新用户最后访问时间
	 * @param user
	 */
	public void updateLastVisit(User user) {
		userDao.updateLastVisit(user);
		if (memCachedClient.get(user.getUsername()) != null) {
//			System.out.println("Modify the "+cUser.getUsername()+"'s last_visit directly: "+cUser.getLast_visit());
			memCachedClient.set(user.getUsername(), user);
			User tmp = (User)(memCachedClient.get(user.getUsername()));
			System.out.println("Update current user's last_visited_date:"+ tmp.getLast_visit());
		}
		
	}
	
	/*
	 * update user's Photo
	 * */
	public void updatePhoto(User user) {
		userDao.updatePhoto(user);
	}
	
}

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

	
	public int updatePassword(String username, String pswd) {
		User cuUser;
		if (userDao.updatePassword(username, pswd) == 1) {
			if ((cuUser = (User) memCachedClient.get(username)) != null) {
				cuUser.setPassword(pswd);
				memCachedClient.set(username, cuUser);
			}
			return 1;
		} else {
			return 0;
		}
	}
	
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
	public int addUser(User user) {
		if (userDao.insertUser(user) == 1) {
			memCachedClient.set(user.getUsername(), user);
			return 1;
		}
		return 0;
	}
	
	/**
	 * 更新用户最后访问时间
	 * @param user
	 */
	public int updateLastVisit(User user) {
		if (userDao.updateLastVisit(user) == 1) {
			if (memCachedClient.get(user.getUsername()) != null) {
				memCachedClient.set(user.getUsername(), user);
			}
			return 1;
		} else {
			return 0;
		}
	}
	
	/*
	 * update user's Photo
	 * */
	public int updatePhoto(User user) {
		if (userDao.updatePhoto(user) == 1) {
			if (memCachedClient.get(user.getUsername()) != null) {
				memCachedClient.set(user.getUsername(), user);
			}
			return 1;
		} else {
			return 0;
		}
	}
	
}

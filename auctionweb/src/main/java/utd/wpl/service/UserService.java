package utd.wpl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utd.wpl.dao.UserDao;
import utd.wpl.pojo.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 查询用户信息
	 * @param username 用户名
	 * @return
	 */
	public User findUser(String username) {
		return userDao.findUserByUserName(username);
	}
	
	/**
	 * 添加新用户
	 * @param user
	 */
	public void addUser(User user) {
		userDao.insertUser(user);
	}
	
	/**
	 * 更新用户最后访问时间
	 * @param user
	 */
	public void updateLastVisit(User user) {
		userDao.updateLastVisit(user);
	}
	
	/*
	 * update user's profile
	 * */
	public void updatePhoto(User user) {
		userDao.updatePhoto(user);
	}
	
}

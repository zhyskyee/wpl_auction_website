package utd.wpl.dao;

import utd.wpl.pojo.User;

public interface UserDao {
	
	User findUserByUserName(String username);
	void insertUser(User user);
	void updateLastVisit(User user);
	void updatePhoto(User user);
}

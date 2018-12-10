package utd.wpl.dao;

import org.apache.ibatis.annotations.Param;

import utd.wpl.pojo.User;

public interface UserDao {
	
	User findUserByUserName(String username);
	int  insertUser(User user);
	int updateLastVisit(User user);
	int  updatePhoto(User user);
	int updatePassword(@Param("username") String username, @Param("pswd") String pswd);
}

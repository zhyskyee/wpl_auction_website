package utd.wpl.dao;

import org.apache.ibatis.annotations.Param;

import utd.wpl.pojo.User;

public interface UserDao {
	
	User findUserByUserName(String username);
	int  insertUser(User user);
	int updateLastVisit(User user);
	int  updateEmail(@Param("username") String username, @Param("email") String email);
	int  updatePhone(@Param("username") String username, @Param("phone") String phone);
	int updatePassword(@Param("username") String username, @Param("pswd") String pswd);
}

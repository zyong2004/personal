package com.mybatis.chapter2;

import java.util.List;

import com.mybatis.bean.User;

public interface IUserDao {

	void save(User user);
	void update(User user);
	void delete(User user);
	User findbyid(String id);
	List<User> findAll();
	List<User> findByName(String name);
	
	List<User>findLike(User user);
}

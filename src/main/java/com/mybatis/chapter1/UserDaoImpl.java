package com.mybatis.chapter1;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.bean.User;
import com.mybatis.util.LogersUtil;
import com.mybatis.util.MyBatisUtils;

public class UserDaoImpl implements IUserDao {

	@Override
	public void save(User user) {
		
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		session.insert("com.mybatis.chapter1.IUserDao.insertUser", user);
		 MyBatisUtils.closeSession(session);
	}

	@Override
	public void update(User user) {
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		session.update("com.mybatis.chapter1.IUserDao.updateUser", user);
		 MyBatisUtils.closeSession(session);
	}


	@Override
	public void delete(User user) {
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		session.delete("com.mybatis.chapter1.IUserDao.deleteUser", user);
	}

	@Override
	public User findbyid(String id) {
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		User user = session.selectOne("com.mybatis.chapter1.IUserDao.selectUSER",1);
		System.out.println(LogersUtil.printParam(user));
		MyBatisUtils.closeSession(session);
		return user;
	}

	@Override
	public List<User> findAll() {
		
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		List<User> users = session.selectList("com.mybatis.chapter1.IUserDao.selectAll");
		for (User user : users) {
			System.out.println(LogersUtil.printParam(user));
		}
	
		 MyBatisUtils.closeSession(session);
		return users;
	}

	@Override
	public List<User> findByName(String name) {
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		List<User> users = session.selectList("com.mybatis.chapter1.IUserDao.selectAll");
		MyBatisUtils.closeSession(session);
		return users;
	}

	
}

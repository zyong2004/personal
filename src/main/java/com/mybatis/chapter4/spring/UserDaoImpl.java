package com.mybatis.chapter4.spring;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.mybatis.bean.User;
import com.mybatis.util.LogersUtil;
import com.mybatis.util.MyBatisUtils;
@Component(value="userDao")
public class UserDaoImpl implements IUserDao {
	
	@Resource(name="sqlSessionTemplate")
	public SqlSessionTemplate template;
	@Override
	public void save(User user) {
//		template.u
//		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		template.insert("com.mybatis.chapter4.IUserDao.insertUser", user);
//		 MyBatisUtils.closeSession(session);
	}

	@Override
	public void update(User user) {
//		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		template.update("com.mybatis.chapter4.IUserDao.updateUser", user);
//		 MyBatisUtils.closeSession(session);
	}


	@Override
	public void delete(User user) {
//		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		template.delete("com.mybatis.chapter4.IUserDao.deleteUser", user);
	}

	@Override
	public User findbyid(String id) {
//		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		User user = template.selectOne("com.mybatis.chapter4.IUserDao.selectUSER",1);
//		System.out.println(LogersUtil.printParam(user));
//		MyBatisUtils.closeSession(session);
		return user;
	}

	@Override
	public List<User> findAll() {
		
//		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		List<User> users = template.selectList("com.mybatis.chapter4.IUserDao.selectAll");
		for (User user : users) {
			System.out.println(LogersUtil.printParam(user));
		}
	
//		 MyBatisUtils.closeSession(session);
		return users;
	}

	@Override
	public List<User> findByName(String name) {
//		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		List<User> users = template.selectList("com.mybatis.chapter4.IUserDao.selectAll");
//		MyBatisUtils.closeSession(session);
		return users;
	}

	public SqlSessionTemplate getTemplate() {
		return template;
	}

	public void setTemplate(SqlSessionTemplate template) {
		this.template = template;
	}

	
}

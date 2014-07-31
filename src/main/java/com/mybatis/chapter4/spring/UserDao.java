package com.mybatis.chapter4.spring;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import com.mybatis.bean.User;
/**
 * 采用抽象类org.mybatis.spring.support.SqlSessionDaoSupport提供SqlSession。
 * @author zhangyong
 *
 */
@Component("userDao1")
public class UserDao extends SqlSessionDaoSupport implements IUserDao {

/*	@Resource(name="sqlSessionFactory")
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated method stub
		super.setSqlSessionFactory(sqlSessionFactory);
	}*/

	@Resource(name="sqlSessionTemplate")
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		// TODO Auto-generated method stub
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}


	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		getSqlSession().insert("com.mybatis.chapter4.IUserDao.insertUser", user);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findbyid(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		
		return getSqlSession().selectList("com.mybatis.chapter4.IUserDao.selectAll");
	}

	@Override
	public List<User> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}

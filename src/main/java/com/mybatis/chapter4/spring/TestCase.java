package com.mybatis.chapter4.spring;

/**
 *   1、采用数据映射器（MapperFactoryBean）的方式，不用写mybatis映射文件，采用注解方式提供相应的sql语句和输入参数。
 *    
 *	2、采用接口org.apache.ibatis.session.SqlSession的实现类org.mybatis.spring.SqlSessionTemplate。
 *
 * 3、采用抽象类org.mybatis.spring.support.SqlSessionDaoSupport提供SqlSession。
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SelectBuilderTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mybatis.bean.User;
import com.mybatis.util.LogersUtil;
import com.mybatis.util.MyBatisUtils;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("./applicationContext.xml")
public class TestCase {

	@Autowired
	@Qualifier("userDao")
	private IUserDao dao;
	@Autowired
	@Qualifier("userDao1")
	private IUserDao dao1;

	@Test
	public void testSelect() {
		List<User> users = dao1.findAll();
		for (User user : users) {
			System.out.println(LogersUtil.printParam(user));
		}
	}
	
	@Test
	public void testMapperSelect() {
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		IUserDao dao  =session.getMapper(IUserDao.class);
		/*List<User> users = dao.findAll();
		
		for (User user : users) {
			System.out.println(LogersUtil.printParam(user));
		}*/
	}
	
}

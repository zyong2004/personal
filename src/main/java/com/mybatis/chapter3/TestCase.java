package com.mybatis.chapter3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SelectBuilderTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.mybatis.bean.User;
import com.mybatis.util.LogersUtil;
import com.mybatis.util.MyBatisUtils;

public class TestCase extends junit.framework.TestCase{

	private static SqlSession session;
	@Override
	protected void setUp() throws Exception {
		session =MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
	}

	@Override
	protected void tearDown() throws Exception {
		MyBatisUtils.closeSession(session);
	}
	@Test
	public void testWhere(){
		Map<String,Object> map = new HashMap();
		map.put("name", "ww");
		map.put("email","wwwww");
		List<User> list = session.selectList("com.mybatis.chapter3.IUserDao.selectWhere", map);
		for (User user : list) {
			System.out.println(LogersUtil.printParam(user));
		}
	}
	@Test
	public void testEach(){
		
		List<String> list = new ArrayList<String>();
		list.add("11");
		list.add("www");
		list.add("test1起");
		List<User> users = session.selectList("com.mybatis.chapter3.IUserDao.selectIn", list);
		for (User user : users) {
			System.out.println(LogersUtil.printParam(user));
		}
	}
	
}

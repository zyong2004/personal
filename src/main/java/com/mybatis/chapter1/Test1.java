package com.mybatis.chapter1;

import java.util.Date;

import org.junit.Test;

import com.mybatis.bean.User;
import com.mybatis.util.LogersUtil;
public class Test1 {

	@Test
	public void testSelect(){
		 IUserDao dao = new UserDaoImpl();
		 User user = dao.findbyid("1");
		System.out.println(LogersUtil.printParam(user));
	}
	@Test
	public void testInsert(){
		User user = new User();
		user.setCreateTime(new Date());
		user.setEmail("testa@163.com");
		user.setName("test1起");
		user.setPassword("test1");
		user.setStatus("1");
		IUserDao dao = new UserDaoImpl();
		dao.save(user);
		
	}
	@Test
	public void testUpdate(){
		IUserDao dao = new UserDaoImpl();
		User user = dao.findbyid("1"); 
		System.out.println(LogersUtil.printParam(user));
		user.setEmail("updatetest1@163.com");
		dao.update(user);
	}
	@Test
	public void testDelete(){
		IUserDao dao = new UserDaoImpl();
		User user =new User();
		user.setId("1");
		dao.delete(user);
	}
	@Test
	public void testFindAll(){
		IUserDao dao = new UserDaoImpl();
		dao.findAll();
	}
}

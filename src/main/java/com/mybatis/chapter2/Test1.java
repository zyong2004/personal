package com.mybatis.chapter2;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.mybatis.bean.User;
import com.mybatis.util.LogersUtil;
import com.mybatis.util.MyBatisUtils;

/**
 * MAVEN工程，打印log日志时，使用的日志文件自动检测src/test/java目录下的log4j.properties文件
 * 使用slf4j  需要日志适配器
 * 
 * @author zhangyong
 * 
 */
public class Test1 {

	static Logger logger = Logger.getLogger(Test1.class);

	@Test
	public void testSelect() {
		IUserDao dao = new UserDaoImpl();
		User user = dao.findbyid("2");
		System.out.println(LogersUtil.printParam(user));
	}

	@Test
	public void testInsert() {
		User user = new User();
		user.setCreateTime(new Date());
		user.setEmail("www@163.com");
		user.setName("wwww起");
		user.setPassword("www1");
		user.setStatus("1");
		IUserDao dao = new UserDaoImpl();
		dao.save(user);

	}

	@Test
	public void testUpdate() {
		IUserDao dao = new UserDaoImpl();
		User user = dao.findbyid("1");
		System.out.println(LogersUtil.printParam(user));
		user.setEmail("updatetest1@163.com");
		dao.update(user);
	}

	@Test
	public void testDelete() {
		IUserDao dao = new UserDaoImpl();
		User user = new User();
		user.setId("1");
		dao.delete(user);
	}

	@Test
	public void testFindAll() {
		IUserDao dao = new UserDaoImpl();
		dao.findAll();
	}

	@Test
	public void testFindByName() {
		// System.out.println(Test1.class.getClassLoader().getResource("log4j.properties"));
		// PropertyConfigurator.configure(Test1.class.getClassLoader().getResource("log4j.properties"));
		IUserDao dao = new UserDaoImpl();
		List<User> user = dao.findByName("ww");
		System.out.println(user);
		for (User user2 : user) {
			System.out.println(LogersUtil.printParam(user2));

		}
		logger.info("=========!!!!!!!!!!!!!!!!!!!");
	}

	public static void main(String[] args) {
		// org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		logger.info("==========");
		IUserDao dao = new UserDaoImpl();
		User u = new User();
		u.setEmail("www");
		u.setName("w");
		List<User> user = dao.findLike(u);
		System.out.println(user);
		for (User user2 : user) {
			System.out.println(LogersUtil.printParam(user2));

		}
	}
	@Test
	public void testFindByMap(){
		Map<String,Object> map = new HashMap<String, Object>();
		User u = new User();
		u.setEmail("testa@163.com");
		u.setName("test1起");
		map.put("u", u);
		//map.put("name", "test1起");
	//	map.put("email", "testa@163.com");
		map.put("start", 0);
		map.put("end", 10);
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		List<User>list = session.selectList("com.mybatis.chapter2.IUserDao.selectByMap", map);
		for (User user : list) {
			System.out.println(LogersUtil.printParam(user));
		}
	}
	
	@Test
	public void testFindByMapKey(){
		Map<String,Object> map = new HashMap<String, Object>();
		User u = new User();
		u.setEmail("testa@163.com");
		u.setName("test1起");
		map.put("u", u);
		//map.put("name", "test1起");
	//	map.put("email", "testa@163.com");
		map.put("start", 0);
		map.put("end", 10);
		SqlSession session = MyBatisUtils.getSqlSession(Constant.CONFIG_PATH);
		//session.selectMap("", "");
		/**
		 * 第二个参数 必须是查询出的对象的一个属性，如果属性值相同，只取一个返回
		 */
		Map<String, Object> map1=session.selectMap("com.mybatis.chapter1.IUserDao.selectAll", "id");
		for(Entry<String, Object> en :map1.entrySet()){
			System.out.println(en.getKey()+":"+en.getValue());
		}
	}

	

}

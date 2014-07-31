package com.mybatis.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {

	private static final String CONFIG_PATH = "src/main/java/com/mybatis/chapter1/chapter1.xml";


	public static SqlSession getSqlSession(String path) {
		SqlSession session = null;
			try {
				InputStream in = new FileInputStream(path);

				SqlSessionFactory factory = new SqlSessionFactoryBuilder()
						.build(in);
				session = factory.openSession();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		return session;
	}

	
	public static MyBatisUtils getInstance(){
		return SingletonHolder.instance;
	}
	
	public static void closeSession(SqlSession session) {
		session.close();
	}
	
	private static class SingletonHolder{
		private static MyBatisUtils instance = new MyBatisUtils();
	}
	
}

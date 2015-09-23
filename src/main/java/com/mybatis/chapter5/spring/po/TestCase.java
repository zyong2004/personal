package com.mybatis.chapter5.spring.po;

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
	private MmgwFileUploadUrlMapper mmgwFileUploadUrlMapper;
	
	@Test
	public void test(){
		
		mmgwFileUploadUrlMapper.queryMmgwFileUrls(new HashMap<String, String>());
	}
}

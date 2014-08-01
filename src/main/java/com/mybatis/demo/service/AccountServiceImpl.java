package com.mybatis.demo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.mybatis.demo.dao.IAccountDao;
import com.mybatis.demo.mapper.Account;
@Component("accountService")
public class AccountServiceImpl<T extends Account> implements IAccountService<T> {
	@Resource
	private IAccountDao<T> dao;
	@Override
	public boolean addAccount(T entity) throws DataAccessException {
		return dao.addAccount(entity);
	}

	@Override
	public T getAccount(Integer id) throws DataAccessException {
		return dao.getAccount(id);
	}

	@Override
	public List<T> getList() throws DataAccessException {
		return dao.getList();
	}

}

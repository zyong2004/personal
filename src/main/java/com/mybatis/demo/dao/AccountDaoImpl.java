package com.mybatis.demo.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mybatis.demo.mapper.Account;
import com.mybatis.demo.mapper.AccountMapper;
@Repository
public class AccountDaoImpl<T extends Account> implements IAccountDao<T> {
	@Resource
	private AccountMapper mapper;

	@Override
	public boolean addAccount(T entity) throws DataAccessException {
		boolean flag = false;
		try {
			mapper.addAccount(entity);
			flag = true;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getAccount(Integer id) throws DataAccessException {
		T entity = null;
		try {
			entity = (T) mapper.getAccountById(String.valueOf(id));
		} catch (DataAccessException e) {
			throw e;
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getList() throws DataAccessException {
		return (List<T>) mapper.getAllAccount();
	}

}

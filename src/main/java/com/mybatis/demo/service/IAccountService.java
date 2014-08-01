package com.mybatis.demo.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface IAccountService<T> {

	public boolean addAccount(T entity) throws DataAccessException;
	public T getAccount(Integer id) throws DataAccessException;
	public List<T> getList() throws DataAccessException;
}

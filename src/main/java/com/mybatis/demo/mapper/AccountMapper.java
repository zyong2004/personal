package com.mybatis.demo.mapper;

import java.util.List;

/**
 * 继承SqlMapper，MyBatis数据操作接口；此接口无需实现类
 * 
 * @author zhangyong
 * 
 */
public interface AccountMapper extends SqlMapper {
	public List<Account> getAllAccount();

	public Account getAccount();

	public Account getAccountById(String id);

	public Account getAccountByNames(String spring);

	public Account getAccountByName(String name);

	public void addAccount(Account account);

	public void editAccount(Account account);

	public void removeAccount(int id);
}

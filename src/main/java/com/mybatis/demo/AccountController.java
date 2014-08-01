package com.mybatis.demo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybatis.demo.mapper.Account;
import com.mybatis.demo.service.IAccountService;
@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private IAccountService<Account> service;

	@RequestMapping("/add")
	public String add(Account acc) {
		System.out.println(acc);
		service.addAccount(acc);
		return "redirect:/account/list.do";
	}

	@RequestMapping("/get")
	public String get(Integer id, Model model) {
		System.out.println("##ID:" + id);
		model.addAttribute(service.getAccount(id));
		return "show";
	}

	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", service.getList());
		return "list";
	}

	@ExceptionHandler(Exception.class)
	public String exception(Exception e, HttpServletRequest request) { // e.printStackTrace();
		request.setAttribute("exception", e);
		return "error";
	}

}

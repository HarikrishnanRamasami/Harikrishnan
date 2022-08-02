package com.example.demo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDAO dao;

	public UserDAO getDao() {
		return dao;
	}

	public void setDao(UserDAO dao) {
		this.dao = dao;
	}

	public void saveUser(User user) {
		getDao().save(user);
	}
	public List<User> checkUser(String uname,String upass){
		
		return getDao().CheckUser(uname, upass);
		
	}
	
	
		
		
		
	

	public List<User> checkUserid(User user) {
		return getDao().checkUserid(user);
	}
}

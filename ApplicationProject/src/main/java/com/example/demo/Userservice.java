package com.example.demo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class Userservice {

	@Autowired
	private UserDAO dao;

	public UserDAO getDao() {
		return dao;
	}

	public void setDao(UserDAO dao) {
		this.dao = dao;
	}
	
	public void saveuser(User user) {
		
		getDao().save(user);
	}
	
	public List<User> checkuser(String name,String password){
		
		return getDao().checkuser(name, password);
	}
	
	public List<User> checkid(User id){
		
		return getDao().checkid();
	}
}

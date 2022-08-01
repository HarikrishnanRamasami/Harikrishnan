package com.example.demo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

@Transactional
public class UserService {
	
	@Autowired
	
	private UserDao userdao;

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}
	
	public void saveuser(User user) {
		getUserdao().save(user);
	}
	
	public List<User> checkuser(String username,String password){
		return getUserdao().checkuser(username, password);
	}
	
	public List<User> checkid(User userid){
		return getUserdao().checkid();
	}
	
	


}

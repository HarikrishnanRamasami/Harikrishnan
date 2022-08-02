package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Integer> {
	
	@Query("from User where UserName=?1 and Password=?2")
	public List<User> checkuser(String name,String password);
	
	@Query("from User order by userid ASC")
	public List<User> checkid();
	
	
}

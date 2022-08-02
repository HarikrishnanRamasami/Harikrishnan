package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {
	
	@Query("from User where name=?1 and password=?2 ")
	public List<User> checkuser(String name,String password);
	
	@Query("from User order by id ASC")
	public List<User> checkid();

}

package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface UserDAO extends JpaRepository<User,Integer> {
	@Query("from User where username=?1 and password1=?2")
	public List<User> CheckUser(String uname,String upass);
	
	
	@Query("from User order by userid ASC")
	public List<User> checkUserid(User user);
}

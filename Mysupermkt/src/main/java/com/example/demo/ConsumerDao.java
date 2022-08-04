package com.example.demo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerDao extends JpaRepository<Consumer,Integer>{
	
	@Query("from Consumer where name=?1 and pass=?2")
	public List<Consumer> CheckUser(String name,String pass);
	
	@Query("from Consumer order by uid ASC")
	public List<Consumer> checkUID(Consumer d);

}

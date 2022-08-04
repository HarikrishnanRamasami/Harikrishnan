package com.example.demo;

import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class Services {

	@Autowired
	private ConsumerDao dao;

	public ConsumerDao getDao() {
		return dao;
	}

	public void setDao(ConsumerDao dao) {
		this.dao = dao;
	}

	public void saveData(Consumer c) {
		getDao().save(c);
	}

	public List<Consumer> checkUser(String name, String pass) {
		return getDao().CheckUser(name, pass);
	}

	public List<Consumer> checkUID(Consumer d) {
		return getDao().checkUID(d);
	}

	public void updateFlag(int a, String name, String pass) {

		List<Consumer> c = getDao().CheckUser(name, pass);
		Iterator<Consumer> itr = c.iterator();

		Consumer cd = itr.next();
		cd.setFlag(a);
		getDao().save(cd);
	}

}

package servic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repdao.Mydao;

@Service
public class Myservice {
	@Autowired
	private Mydao dao;
	public Mydao getDao() {
		return dao;
	}
	public void setDao(Mydao dao) {
		this.dao = dao;
	}
	public void service() {
		System.out.println("Myservice called.....");
		dao.dao();
	}
}

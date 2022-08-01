package repdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Mydao {
	@Autowired
	private Mydao2 dao2;
	public Mydao2 getDao2() {
		return dao2;
	}
	public void setDao2(Mydao2 dao2) {
		this.dao2 = dao2;
	}
	public void dao() {
		System.out.println("Mydao called....");
		dao2.dao2();
	}

}
@Repository
class Mydao2{
	public void dao2() {
		System.out.println("dao2 called.....");
	}
}

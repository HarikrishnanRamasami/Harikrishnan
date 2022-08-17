package restcontroller_3;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {
	private int esal;
	private long eid;
	private String ename;

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(long eid, String ename, int esal) {
		super();
		this.eid = eid;
		this.ename = ename;
		this.esal = esal;
	}

	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public int getEsal() {
		return esal;
	}

	public void setEsal(int esal) {
		this.esal = esal;
	}

}
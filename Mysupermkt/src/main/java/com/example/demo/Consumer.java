package com.example.demo;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Consumer {
	
	@Id
	private int uid;
	
	@NotNull
	@Size(max = 15, min = 5)
	private String name;
	
	@Size(max = 15, min = 5)
	private String pass;
	
	private long mno;

	@Size(max = 15, min = 3)
	private String city;

	private int age;
	
	private int flag;

	@Size(max = 50, min = 10)
	private String address;

	private String img;
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public long getMno() {
		return mno;
	}

	public void setMno(long mno) {
		this.mno = mno;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}

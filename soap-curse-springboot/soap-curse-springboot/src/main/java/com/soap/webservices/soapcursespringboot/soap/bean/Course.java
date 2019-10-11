package com.soap.webservices.soapcursespringboot.soap.bean;

public class Course {
	private int id;
	private String name;
	private String desription;
	
	public Course(int id, String name, String desription) {
		super();
		this.id = id;
		this.name = name;
		this.desription = desription;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}


	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", desription=" + desription + "]";
	}
	
	
	
	
	
}

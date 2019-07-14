package org.ecore.ecore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class Teacher {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	private String school;


	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSchool() {
		return school;
	}
	
	public Teacher() {
		
	}
	
	public Teacher(String name, String school) {
		this.name = name;
		this.school = school;
		
	}


}

package org.ecore.ecore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class School {

	@GeneratedValue
	@Id
	private long id;
	
	private String name;

	public School(String name) {
		this.name = name;
	}
	
	public School() {
		
	}
	public long getId() {
		
		return id;
	}

	public String getName() {
		return name;
	}
}

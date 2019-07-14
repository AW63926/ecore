package org.ecore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class School {

	@GeneratedValue
	@Id
	private long id;
	
	private String name;

	private String district;

	private String address;

	private String mapUrl;

	public School(String name, String district, String address, String mapUrl) {
		this.name = name;
		this.district = district;
		this.address = address;
		this.mapUrl = mapUrl;
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

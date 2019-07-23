package org.ecore.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.Arrays;

@Entity
public class Teacher {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	private String specialty;
	
	@ManyToOne
	private School school;
	
	@ManyToMany(mappedBy = "needs")
	private Collection<Need> needs;


	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public School getSchool() {
		return school;
	}
	

	public String getSpecialty() {
		return specialty;
	}
	public Collection<Need>getNeeds(){
		return needs;
	}

	public Teacher () {
	}

	public Teacher(String name, String specialty, School school, Need...needs) {
		this.name = name;
		this.specialty = specialty;
		this.school = school;
		this.needs = new HashSet<>(Arrays.asList(needs));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

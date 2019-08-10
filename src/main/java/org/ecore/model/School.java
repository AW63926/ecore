package org.ecore.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class School {

	@GeneratedValue
	@Id
	private long id;

	private String name;

	private String district;

	private String address;

	private String mapUrl;

	@JsonIgnore
	@OneToMany(mappedBy = "school")
	private Collection<Teacher> teachers;

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

	public String getAddress() {

		return address;
	}

	public String getDistrict() {
		return district;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public Collection<Teacher> getTeachers() {
		return teachers;
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
		School other = (School) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

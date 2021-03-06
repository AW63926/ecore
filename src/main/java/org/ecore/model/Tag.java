package org.ecore.model;


import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tag {
	
	@GeneratedValue
	@Id
	private long id;
	public String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "tags")
	private Collection<Need> needs;
	
	@ManyToMany(mappedBy = "tags")
	private Collection<Material> materials;

	public Tag(String name) {
		this.name = name;

	}
	
	public Tag() {
		
	}
	

	public long getId() {
		return id;

	}

	public String getName() {
		return name;
	}

	public Collection<Need> getNeeds() {
		return needs;
	}
	
	public Collection<Material> getMaterials() {
		return materials;
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
		Tag other = (Tag) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	

	}


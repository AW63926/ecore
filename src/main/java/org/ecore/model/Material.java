package org.ecore.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;



@Entity
public class Material {
	
	@GeneratedValue	
	@Id
	long id;
	private String name;
	
	private int quantity;
	
	private String descMaterial;
	
	@ManyToMany
	private Collection<Tag> tags;

	public Material(String name, int quantity, String descMaterial, Tag...tags) {
		this.name = name;
		this.quantity = quantity;
		this.descMaterial = descMaterial;
		this.tags = new HashSet<> (Arrays.asList(tags));
		
	}
	
	public Material() {
		
	}

	public long getId() {
		
		return id;
	}

	public String getName() {
		
		return name;
	}
	
	public int getQuantity() {
		
		return quantity;
	}
	
	public String getDescMaterial () {
		
		return descMaterial;
	}

	public Collection<Tag> getTags() {
		
		return tags;
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
		Material other = (Material) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

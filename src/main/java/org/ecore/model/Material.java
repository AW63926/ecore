package org.ecore.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Material {
	
	@GeneratedValue	
	@Id
	long id;
	private String name;
	
	private int quantity;
	
	private String descMaterial;
	
	@OneToMany(mappedBy = "material")
	private Collection<PostedResource> postedResources;
	
	@JsonIgnore
	@ManyToMany
	private Collection<Tag> tags;
	
	@JsonIgnore
	@ManyToOne
	private Teacher teacher;

	public Material(String name, int quantity, String descMaterial, Teacher teacher, Tag...tags) {
		this.name = name;
		this.quantity = quantity;
		this.descMaterial = descMaterial;
		this.teacher = teacher;
		this.tags = new HashSet<> (Arrays.asList(tags));
		
	}
	
	public Material() {
		
	}
	
	
	public Collection<PostedResource> getPostedResources() {
		return postedResources;
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
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void addTag(Tag tagToAdd) {
		tags.add(tagToAdd);	
		
	}
	
	public void removeTag(Tag tagToRemove) {
		tags.remove(tagToRemove);		
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

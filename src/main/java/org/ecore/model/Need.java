package org.ecore.model;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Need {
	
	@GeneratedValue
	@Id
	private long id;
	
	private String name;
	private int quantity;

	@Lob
	public String descNeed;
	
	@ManyToOne
	private Teacher teacher;

	@ManyToMany
	private Collection<Tag> tags;

	public Need(String name, int quantity, String descNeed, Teacher teacher, Tag...tags) {
		this.name = name;
		this.quantity = quantity;
		this.descNeed = descNeed;
		this.teacher = teacher;
		this.tags = new HashSet<>(Arrays.asList(tags));

	}
	
	public Need() {
		
	}


	public long getId() {
		return id;
	}

	public Collection<Tag> getTags() {
		return tags;
	}


	public String getName() {
	return name;
	
}
	public int getQuantity() {
		return quantity;
	}
	
	public Teacher getTeacher() {
		return teacher;
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
		Need other = (Need) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void addTag(Tag tagToAdd) {
		tags.add(tagToAdd);		
	}
	
	public void removeTag(Tag tagToRemove) {
		tags.remove(tagToRemove);		
	}


	

}

package org.ecore.model;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

@Entity
public class Need {
	
	@GeneratedValue
	@Id
	private Long id;
	
	private String name;
	private int quantity;

	@Lob
	public String descNeed;

//	@ManyToMany(mappedBy = "needs")
//	private Collection<Teacher> teachers;

	//JsonIgnore
	@ManyToMany
	private Collection<Tag> tags;

	public Need(String name, int quantity, String descNeed, Tag... tags) {
		this.name = name;
		this.quantity = quantity;
		this.descNeed = descNeed;
		this.tags = new HashSet<>(Arrays.asList(tags));

	}
	
	public Need() {
		
	}

	public Long getId() {
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


}

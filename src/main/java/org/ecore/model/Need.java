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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

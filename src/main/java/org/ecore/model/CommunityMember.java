package org.ecore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CommunityMember {
	
	@GeneratedValue
	@Id
	private long id;
	
	private String name;
	private String email;
	
	public CommunityMember() {
		
	}
	

	public CommunityMember(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public long getId() {

		return id;
	}


	public String getName() {

		return name;
	}
	
	public String getEmail() {
		
		return email;
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
		CommunityMember other = (CommunityMember) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

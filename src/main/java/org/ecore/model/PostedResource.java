package org.ecore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PostedResource {
	
	private String resourceUrl;
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private Material material;
	
	public long getId() {
		return id;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	protected PostedResource() {
		
	}
	
	public PostedResource(String resourceUrl, Material material) {
		this.resourceUrl = resourceUrl;
		this.material = material;
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
		PostedResource other = (PostedResource) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	

}

package org.ecore.model;


import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
	
	@GeneratedValue
	@Id
	private Long id;
	public String name;

	@ManyToMany(mappedBy = "tags")
	private Collection<Need> needs;

	public Tag(String name) {
		this.name = name;

	}
	
	public Tag() {
		
	}

	public Long getId() {
		return id;

	}

	public String getName() {
		return name;
	}

	public Collection<Need> getNeeds() {
		return needs;
	}

//	public Collection<String> getTagUrls() {
//		Collection<String> urls = new ArrayList<>();
//		for (Tag t : tags) {
//			urls.add(format("/show-tags/%d/tags/%s", this.getId(), t.getName()));
//		}
//		return urls;
	}


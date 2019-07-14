package org.ecore.model;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.ManyToMany;

public class Tag {

	private Long id;
	public String name;
	public Collection<Tag> tags;

	@ManyToMany(mappedBy = "tags")
	private Collection<Need> needs;

	public Tag(String name, Need... needs) {
		this.name = name;
		this.needs = new HashSet<>(Arrays.asList(needs));

	}

	public Long getId() {
		return id;

	}

	public String getName() {
		return name;
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public Collection<Need> getNeeds() {
		return needs;
	}

	public Collection<String> getTagUrls() {
		Collection<String> urls = new ArrayList<>();
		for (Tag t : tags) {
			urls.add(format("/show-tags/%d/tags/%s", this.getId(), t.getName()));
		}
		return urls;
	}

}
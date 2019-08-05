package org.ecore.repository;

import java.util.Collection;

import org.ecore.model.Material;

import org.ecore.model.Tag;
import org.springframework.data.repository.CrudRepository;


public interface MaterialRepository extends CrudRepository<Material, Long> {

	Material findByName(String materialName);
	
	Collection<Material> findByTagsContains(Tag tag);

}

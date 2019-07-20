package org.ecore.repository;

import java.util.Collection;

import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.springframework.data.repository.CrudRepository;


public interface NeedRepository extends CrudRepository<Need, Long> {

	Collection<Need> findByTagsContains(Tag chess);

	Collection<Need> findByTagsId(Long id);

	Need findByName(String needName);

	Collection<Need> findAllByOrderByNameAsc();

	Need findByNameIgnoreCaseLike(String NeedName);
}


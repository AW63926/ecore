package org.ecore.repository;

import java.util.Collection;

import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

Tag findByName(String tagName);
Tag findByNameIgnoreCaseLike(String tagName);

Collection<Tag>findByNeedsContains(Need need);

}

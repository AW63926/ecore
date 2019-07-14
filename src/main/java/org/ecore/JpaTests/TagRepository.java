package org.ecore.JpaTests;

import org.ecore.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {


}

package org.ecore.repository;

import java.util.Optional;

import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface SchoolRepository extends CrudRepository<School, Long>{

	School getByNameIgnoreCaseLike(String name);

	School findByNameIgnoreCaseLike(String name);

	School findByName(String name);

}

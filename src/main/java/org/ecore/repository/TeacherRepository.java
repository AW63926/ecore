package org.ecore.repository;

import java.util.Collection;
import java.util.Optional;

import org.ecore.model.Material;
import org.ecore.model.Need;
import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

	Collection<Teacher> findBySchoolContains(School school);

	Teacher findByNameIgnoreCaseLike(String teacherName);

	Teacher findByMaterials(Material material);

	Teacher findByNeeds(Need need);

	Teacher findByEmail(String emailAddress);



}

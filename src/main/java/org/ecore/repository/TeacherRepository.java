package org.ecore.repository;

import java.util.Collection;

import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

	Collection<Teacher> findBySchoolContains(School school);

	Teacher findByNameIgnoreCaseLike(String teacherName);

}

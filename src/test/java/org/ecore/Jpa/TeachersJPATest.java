package org.ecore.Jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Teacher;
import org.ecore.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest

public class TeachersJPATest {

	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private TeacherRepository teacherRepo;
	
	@Test
	public void shouldSaveAndLoadTeacher() {
		Teacher teacher = teacherRepo.save(new Teacher("name", "school"));
		long teacherId = teacher.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Teacher> result = teacherRepo.findById(teacherId);
		teacher = result.get();
		assertThat(teacher.getName(), is("name"));
		
	}
	
	@Test
	public void shouldGenerateTeacherId() {
		Teacher teacher = teacherRepo.save(new Teacher("name", "school"));
		long teacherId = teacher.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(teacherId, is(greaterThan(0L)));
	}

}


package org.ecore.JpaTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.filestorage.StorageService;
import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.ecore.repository.SchoolRepository;
import org.ecore.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest

public class TeachersJPATest {

	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private TeacherRepository teacherRepo;
	
	@Resource
	private SchoolRepository schoolRepo;
	
	@MockBean
	private StorageService storage;
	
	@Test
	public void shouldSaveAndLoadTeacher() {
		School school1 = schoolRepo.save(new School ("name", "dis", "add", "map" ));
		Teacher teacher = teacherRepo.save(new Teacher("name", "specialty", school1, "email"));
		long teacherId = teacher.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Teacher> result = teacherRepo.findById(teacherId);
		teacher = result.get();
		assertThat(teacher.getName(), is("name"));
		
	}
	
	@Test
	public void shouldGenerateTeacherId() {
		School school1 = schoolRepo.save(new School ("name", "dis", "add", "map" ));
		Teacher teacher = teacherRepo.save(new Teacher("name", "specialty", school1, "email"));
		long teacherId = teacher.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(teacherId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldEstablishSchoolToTeachersRelationships() {
		School school1 = schoolRepo.save(new School ("name", "dis", "add", "map" ));
		long schoolId = school1.getId();
		
		Teacher teacher1 = teacherRepo.save(new Teacher("name", "spec", school1, "email"));
		Teacher teacher2 = teacherRepo.save(new Teacher("name1", "speci", school1, "email"));
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<School> result = schoolRepo.findById(schoolId);
		school1 = result.get();
		assertThat(school1.getTeachers(), containsInAnyOrder(teacher1, teacher2));
	}
	

}


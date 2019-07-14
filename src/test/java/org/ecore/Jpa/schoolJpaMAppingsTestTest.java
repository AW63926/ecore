package org.ecore.Jpa;

import java.util.Optional;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import javax.annotation.Resource;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ecore.model.School;
import org.ecore.repository.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class schoolJpaMAppingsTestTest {

	@Resource
	SchoolRepository schoolRepo;
	
	@Resource
	TestEntityManager entityManager;
	
	@Test
	public void shouldSaveAndLoadSchool() {
		School school = schoolRepo.save(new School("School"));
		long schoolId = school.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<School> result = schoolRepo.findById(schoolId);
		school = result.get();
		assertThat(school.getName(), is("School"));
	}
	
	@Test
	public void shouldGenerateSchoolId() {
		School school =schoolRepo.save(new School("School"));
		long schoolId = school.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(schoolId, is(greaterThan(0L)));
	}
}


package org.ecore.JpaTests;

import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


import org.ecore.model.School;
import org.ecore.repository.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest

public class SchoolJpaTest {
	
	@Resource
	SchoolRepository schoolRepo;
	
	@Resource
	EntityManager entityManager;

	@Test
	public void shouldSaveAndLoadSchool() {
		School school = schoolRepo.save(new School("name", "district", "address", "mapUrl"));
		long schoolId = school.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<School> result = schoolRepo.findById(schoolId);
		school = result.get();
		assertThat(school.getName(), is("name"));
		
	}
	
	@Test
	public void shouldGenerateSchoolId() {
		School school = schoolRepo.save(new School("name", "district", "address", "mapUrl"));
		long schoolId = school.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(schoolId, is(greaterThan(0L)));
	}
}

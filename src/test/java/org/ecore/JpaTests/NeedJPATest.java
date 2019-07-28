package org.ecore.JpaTests;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Need;
import org.ecore.model.School;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.ecore.repository.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class NeedJPATest {

	
	@Resource
	private TestEntityManager entityManager;
	
	@Resource 
	private NeedRepository needRepo;
	
	@Resource
	private TagRepository tagRepo;
	
	@Resource
	private TeacherRepository teacherRepo;
	
	@Resource
	private SchoolRepository schoolRepo;
	
	private Teacher teacher;
	
	private Teacher teacher2;
	
	private Tag tag;
	
	private Tag tag2;
	
	
	@Test 
	public void shouldSaveAndLoadNeed() {
		Need need = needRepo.save(new Need("name",5,"descNeed", teacher, tag, tag2));
		long needId = need.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Need>result = needRepo.findById(needId);
		need = result.get();
		assertThat(need.getName(), is("name"));
	}
	
	@Test 
	public void shouldGenerateNeedById () {
		Need need = needRepo.save(new Need("name", 5, "descNeed", teacher, tag, tag2));
		long needId = need.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(needId, is(greaterThan(0L)));
		
	}
	//Adam, this test is also failing and making our JPA mappings to fail as well
	@Test 
	public void shouldEstablishNeedToTagRelationships() {
		Tag chess = tagRepo.save(new Tag("chess"));
		Tag crayons = tagRepo.save(new Tag("crayons"));
	
		Need need = new Need("Chess", 1, "chessclub", teacher, chess, crayons);
		need = needRepo.save(need);
		long needId = need.getId();
	
		entityManager.flush();
		entityManager.clear();
	
		Optional<Need> result = needRepo.findById(needId);
		need = result.get();
	
		assertThat(need.getTags(), containsInAnyOrder(chess,crayons));
}
	@Test
	public void shouldSaveNeedToTeacherRelationship() {
		
		School school2 = new School("name", "district", "address", "map");
		schoolRepo.save(school2);

		Teacher teacher3 = new Teacher("name", "specialty", school2);
		teacherRepo.save(teacher3);
		long teacherId = teacher3.getId();
		
		Tag tag3 = new Tag("name");
		Tag tag4 = new Tag("name2");
		tagRepo.save(tag3);
		tagRepo.save(tag4);
		
		Need need = new Need("name", 3, "descrip", teacher3, tag3, tag4); 
		needRepo.save(need);
		
		Need need2 = new Need("name2", 4, "des", teacher3, tag3, tag4);
		needRepo.save(need2);
		
		entityManager.flush();
		entityManager.clear();

		Optional <Teacher>result = teacherRepo.findById(teacherId);
		teacher3 = result.get();
		assertThat(teacher3.getNeeds(), containsInAnyOrder(need, need2));
		
	}


}



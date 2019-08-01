package org.ecore.JpaTests;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.Collection;
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
	public void shouldFindNeedsForTag() {
		Tag crayons = tagRepo.save(new Tag("crayons"));
		
		Need need1 = needRepo.save(new Need("Need1", 1, "chessclub", teacher, crayons));
		Need need2 = needRepo.save(new Need("Need2", 1, "school supplies", teacher2, crayons));
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Need> needsForTag = needRepo.findByTagsContains(crayons);
		assertThat(needsForTag, containsInAnyOrder(need1, need2));
	}
	
	@Test
	public void shouldFindNeedsForTagId() {
		Tag crayons = tagRepo.save(new Tag("crayons"));
		long tagId = crayons.getId();
		
		Need need1 = needRepo.save(new Need("Need1", 1, "chessclub", teacher, crayons));
		Need need2 = needRepo.save(new Need("Need2", 1, "school supplies", teacher2, crayons));
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Need> needForTag = needRepo.findByTagsId(tagId);
		assertThat(needForTag, containsInAnyOrder(need1, need2));
		
	}
	
	@Test
	public void shouldSaveTagsToNeed() {
		Tag crayons = tagRepo.save(new Tag("crayons"));
		long tagId = crayons.getId();
		
		Need need1 = needRepo.save(new Need("Need1", 1, "chessclub", teacher, crayons));

		Tag chess = tagRepo.save(new Tag("chess"));
		long tagId2 = chess.getId();
		
		need1.addTag(chess);
		needRepo.save(need1);
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Need>result = needRepo.findById(need1.getId());
		Need retrieveNeed = result.get();
		
		assertThat(retrieveNeed.getTags(), containsInAnyOrder(crayons, chess));
		
		Optional<Tag>result2 = tagRepo.findById(chess.getId());
		Tag retrieveTag = result2.get();
		assertThat(retrieveTag.getNeeds(), contains(need1));
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



package org.ecore.JpaTests;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
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
	
	@Test 
	public void shouldSaveAndLoadNeed() {
		Need need = needRepo.save(new Need("name",5,"descNeed"));
		long needId = need.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Need>result = needRepo.findById(needId);
		need = result.get();
		assertThat(need.getName(), is("name"));
	}
	
	@Test 
	public void shouldGenerateNeedById () {
		Need need = needRepo.save(new Need("name", 5, "descNeed"));
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
	
		Need need = new Need("Chess", 1, "chessclub", chess, crayons);
		need = needRepo.save(need);
		long needId = need.getId();
	
		entityManager.flush();
		entityManager.clear();
	
		Optional<Need> result = needRepo.findById(needId);
		need = result.get();
	
		assertThat(need.getTags(), containsInAnyOrder(chess,crayons));
}

	}



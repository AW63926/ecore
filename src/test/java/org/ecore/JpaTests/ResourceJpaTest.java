package org.ecore.JpaTests;

import java.util.Optional;

import javax.annotation.Resource;
import static org.hamcrest.Matchers.*;
import org.ecore.model.Material;
import org.ecore.model.Tag;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import static org.junit.Assert.assertThat;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class ResourceJpaTest {

	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private MaterialRepository materialRepo;
	
	@Resource
	private TagRepository tagRepo;
	
	@Test
	public void shouldSaveAndLoadMaterial () {
		 Material material = materialRepo.save(new Material("name", 1, "descNeed"));
		 long materialId = material.getId();
		 
		 entityManager.flush();
		 entityManager.clear();
		 
		 Optional<Material> result = materialRepo.findById(materialId);
		 material = result.get();
		 assertThat(material.getName(), is ("name"));
		 		 
	}
	
	@Test
	public void shouldGenerateMaterialById () {
		Material material = materialRepo.save(new Material("name", 1, "descNeed"));
		long materialId = material.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(materialId, is(greaterThan(0L)));
		
		
	}
	
	@Test
	public void shouldEstalishMaterialTOTagRelationship() {
		Tag tag1 = tagRepo.save(new Tag("tag1"));
		Tag tag2 = tagRepo.save(new Tag("tag2"));
		
		Material material = materialRepo.save(new Material("name", 1, "descNeed", tag1, tag2));
		long materialId = material.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Material> result = materialRepo.findById(materialId);
		 material = result.get();
		
		
		
		assertThat(material.getTags(), containsInAnyOrder(tag1, tag2));
		
		
		
	}
	
}

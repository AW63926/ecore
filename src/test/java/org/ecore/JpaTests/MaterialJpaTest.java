package org.ecore.JpaTests;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.filestorage.StorageService;
import org.ecore.model.Material;
import org.ecore.model.School;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.SchoolRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class MaterialJpaTest {

	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private MaterialRepository materialRepo;
	
	@Resource
	private TagRepository tagRepo;
	
	@Resource
	private TeacherRepository teacherRepo;
	
	@Resource
	private SchoolRepository schoolRepo;
	
	@MockBean
	private StorageService storage;
	
	
	
	
	private Teacher teacher;
	
	@Test
	public void shouldSaveAndLoadMaterial () {
		 Material material = materialRepo.save(new Material("name", 1, "descNeed", teacher));
		 long materialId = material.getId();
		 
		 entityManager.flush();
		 entityManager.clear();
		 
		 Optional<Material> result = materialRepo.findById(materialId);
		 material = result.get();
		 assertThat(material.getName(), is ("name"));
		 		 
	}
	
	@Test
	public void shouldGenerateMaterialById () {
		Material material = materialRepo.save(new Material("name", 1, "descNeed", teacher));
		long materialId = material.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(materialId, is(greaterThan(0L)));
		
		
	}
	
	@Test
	public void shouldEstalishMaterialTOTagRelationship() {
		Tag tag1 = tagRepo.save(new Tag("tag1"));
		Tag tag2 = tagRepo.save(new Tag("tag2"));
		
		Material material = materialRepo.save(new Material("name", 1, "descNeed", teacher, tag1, tag2));
		long materialId = material.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Material> result = materialRepo.findById(materialId);
		 material = result.get();
		
		
		
		assertThat(material.getTags(), containsInAnyOrder(tag1, tag2));
		
		
		
	}
	
}

package org.ecore.JpaTests;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.filestorage.StorageService;
import org.ecore.model.Tag;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class TagJpaTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private NeedRepository needRepo;

	@Resource
	private TagRepository tagRepo;
	
	@MockBean
	private StorageService storage;

	@Test
	public void shouldSaveAndLoadTag() {
		Tag tag = tagRepo.save(new Tag("name"));
		long tagId = tag.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Tag> result = tagRepo.findById(tagId);
		tag = result.get();
		assertThat(tag.getName(), is("name"));
	}

	@Test
	public void shouldGenerateTagById() {
		Tag tag = tagRepo.save(new Tag("name"));
		long tagId = tag.getId();

		entityManager.flush();
		entityManager.clear();

		assertThat(tagId, is(greaterThan(0L)));

	}
}

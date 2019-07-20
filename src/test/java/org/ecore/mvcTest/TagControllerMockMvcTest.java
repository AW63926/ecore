package org.ecore.mvcTest;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Collection;
import java.util.Arrays;

import org.ecore.controller.TagController;
import org.ecore.model.Tag;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(TagController.class)
public class TagControllerMockMvcTest {

	@Resource
	private MockMvc mvc;

	@MockBean
	private TagRepository tagRepo;

	@Mock
	private Tag tag;

	@MockBean
	private NeedRepository needRepo;

	@Mock
	private Tag anotherTag;

	@Test
	public void shouldRouteToSingleTagView() throws Exception {
		long tagId = 1;
		when(tagRepo.findById(tagId)).thenReturn(Optional.of(tag));

		mvc.perform(get("/tag?id=1")).andExpect(view().name(is("tag")));
	}

	@Test
	public void shouldBeOkForSingleTag() throws Exception {
		long tagId = 1;
		when(tagRepo.findById(tagId)).thenReturn(Optional.of(tag));

		mvc.perform(get("/tag?id=1")).andExpect(status().isOk());
	}

	@Test
	public void ShouldNotRouteToSingletag() throws Exception {
		mvc.perform(get("/tag?id=1")).andExpect(status().isNotFound());

	}
//Adam, this is the only tag test that is failing
	@Test
	public void shouldPutSingleTagIntoModel() throws Exception {
		when(tagRepo.findById(1L)).thenReturn(Optional.of(tag));

		mvc.perform(get("/tag?id=1")).andExpect(model().attribute("tags", is(tag)));

	}

	@Test
	public void shouldRouteToAllTagsView() throws Exception {
		mvc.perform(get("/all-tags")).andExpect(view().name(is("all-tags")));
	}

	@Test
	public void shouldPutAllTagsIntoModel() throws Exception {
		Collection<Tag> allTags = Arrays.asList(tag, anotherTag);
		when(tagRepo.findAll()).thenReturn(allTags);

		mvc.perform(get("/all-tags")).andExpect(model().attribute("tags", allTags));
	}
}

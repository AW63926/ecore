package org.ecore.controllerTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.ecore.controller.TagController;
import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.ecore.notFoundException.TagNotFoundException;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class TagControllerTest {
	private static final long NEED_ID = 1L;
	@InjectMocks
	private TagController underTest;

	@Mock
	private Tag tag;
	Long tagId;

	@Mock
	private Tag anotherTag;

	@Mock
	private TagRepository tagRepo;

	@Mock
	private Model model;
	
	@Mock
	private NeedRepository needRepo;
	
	@Mock
	private Need need;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void shouldAddSingleTagToModel() throws TagNotFoundException {
		long arbitraryTagId = 1;
		when(tagRepo.findById(arbitraryTagId)).thenReturn(Optional.of(tag));

		underTest.findOneTag(arbitraryTagId, model);
		verify(model).addAttribute("tags", tag);
	}

	@Test
	public void shouldAddAllTagsToModel() {
		Collection<Tag> allTags = Arrays.asList(tag, anotherTag);
		when(tagRepo.findAll()).thenReturn(allTags);
		when(needRepo.findById(NEED_ID)).thenReturn(Optional.of(need));

		underTest.findAllTags(model);
		verify(model).addAttribute("tags", allTags);
	}
}

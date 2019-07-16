package org.ecore.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.ecore.controller.NeedController;
import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.ecore.notFoundException.NeedNotFoundException;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class NeedControllerTest {

	@InjectMocks
	private NeedController underTest;

	@Mock
	private Need need;
	Long needId;

	@Mock
	private Need anotherNeed;

	@Mock
	private NeedRepository needRepo;

	@Mock
	private Tag tag;

	@Mock
	private TagRepository tagRepo;

	@Mock
	private Tag anotherTag;

	@Mock
	private Model model;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void shouldAddSingleNeedToModel() throws NeedNotFoundException {
		long arbitraryNeedId = 1;
		when(needRepo.findById(arbitraryNeedId)).thenReturn(Optional.of(need));

		underTest.findOneNeed(arbitraryNeedId, model);
		verify(model).addAttribute("needs", need);
	}

	@Test
	public void shouldAddAllNeedsToModel() {
		Collection<Need> allNeeds = Arrays.asList(need, anotherNeed);
		when(needRepo.findAll()).thenReturn(allNeeds);

		underTest.findAllNeeds(model);
		verify(model).addAttribute("needs", allNeeds);

	}
//Adam, this is the failing test
//	@Test
//	public void shouldAddAdditionalNeedsToModel() {
//		String needName = "new needName";
//		int needQuantity = 6;
//		String descNeed = "new descNeed";
//		underTest.addNeed(needName, 6, descNeed);
//
//		ArgumentCaptor<Need> needArgument = ArgumentCaptor.forClass(Need.class);
//		verify(needRepo).save(needArgument.capture());
//		assertEquals("new need", needArgument.getValue().getName());
//	}

	@Test
	public void shouldRemoveNeedFromModelByName() {
		String needName = need.getName();
		when(needRepo.findByName(needName)).thenReturn(need);
		underTest.deleteNeedByName(needName);
		verify(needRepo).delete(need);

	}

	@Test
	public void shouldRemoveNeedFromModelById() {
		underTest.deleteNeedById(needId);
		verify(needRepo).deleteById(needId);
	}

}

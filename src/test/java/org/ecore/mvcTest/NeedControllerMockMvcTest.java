package org.ecore.mvcTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.controller.NeedController;
import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.repository.CommunityMemberRepository;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(NeedController.class)
public class NeedControllerMockMvcTest {

	@Resource
	private MockMvc mvc;

	@MockBean
	private NeedRepository needRepo;
	
	@MockBean
	private TeacherRepository teacherRepo;
	
	@MockBean
	private CommunityMemberRepository communityMemberRepo;

	@Mock
	private Need need;
	
	@MockBean
	private TagRepository tagRepo;

	@Mock
	private Need anotherNeed;
	
	@Mock
	private Teacher teacher;
	
	@Mock
	private Tag tag;
	
	@Test
	public void shouldRouteToSingleNeedView() throws Exception {
		long needId = 1;
		when(needRepo.findById(needId)).thenReturn(Optional.of(need));

		mvc.perform(get("/need?id=1")).andExpect(view().name(is("need")));
	}
	
	@Test
	public void shouldBeOkForSingleNeed() throws Exception {
		long needId =1;
		when(needRepo.findById(needId)).thenReturn(Optional.of(need));
		
		mvc.perform(get("/need?id=1")).andExpect(status().isOk());
	}
	
	//Adam, this is the test that's not passing
	@Test
	public void shouldNotBeOkForSingleNeed() throws Exception {
		
		mvc.perform(get("/need?id=1")).andExpect(status().isNotFound());
	
	}
	
	@Test
	public void shouldPutSingleNeedIntoModel() throws Exception {
		when(needRepo.findById(1L)).thenReturn(Optional.of(need));
		
		mvc.perform(get("/need?id=1")).andExpect(model().attribute("needs", is(need)));
		
	}
	@Test
	public void shouldRouteToAllNeedsView() throws Exception {
		mvc.perform(get("/all-needs")).andExpect(view().name(is("all-needs")));
	}
	
	@Test
	public void shouldPutAllNeedsIntoModel() throws Exception {
		Collection<Need> allNeeds = Arrays.asList(need, anotherNeed);
		when(needRepo.findAll()).thenReturn(allNeeds);
		
		mvc.perform(get("/all-needs")).andExpect(model().attribute("needs", allNeeds));
	}
}

	





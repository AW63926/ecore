package org.ecore.mvcTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.*;


import org.ecore.controller.CommunityMemberContoller;
import org.ecore.model.CommunityMember;
import org.ecore.repository.CommunityMemberRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CommunityMemberContoller.class)
public class CommunityMemberMockMvcTest {
	
	@Resource
	private MockMvc mvc;

	@MockBean
	private CommunityMemberRepository communityMemberRepo;
	
	@Mock
	CommunityMember communityMember;
	
	@Mock
	CommunityMember anotherCommunityMember;
	
	@Test
	public void shouldRouteToSingleCommunityMemberView() throws Exception {
		long communityMemberId = 1;
		when(communityMemberRepo.findById(communityMemberId)).thenReturn(Optional.of(communityMember));
		
		mvc.perform(get("/community-member?id=1")).andExpect(view().name(is("community-member")));
	}
	
	@Test
	public void shouldRouteToSingleCommunityMember() throws Exception {
		long communityMemberId =1;
		when(communityMemberRepo.findById(communityMemberId)).thenReturn(Optional.of(communityMember));
		
		mvc.perform(get("/community-member?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void ShouldNotRouteToSingleCOmmunityMember() throws Exception {
		mvc.perform(get("/community-member?id=1")).andExpect(status().isNotFound());
		
	}
	
	@Test
	public void shouldPutSingleCommunityMemberIntoModel() throws Exception {
		when(communityMemberRepo.findById(1L)).thenReturn(Optional.of(communityMember));
		
		mvc.perform(get("/community-member?id=1")).andExpect(model().attribute("community-members", is(communityMember)));
		
	}
	
	@Test
	public void shouldRouteToAllCommunityMembersView() throws Exception {
		mvc.perform(get("/all-community-members")).andExpect(view().name(is("all-community-members")));
	}
	
	@Test
	public void voidShouldBeOkForAllCommunityMembers() throws Exception {
		
		mvc.perform(get("/all-community-members")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldPutAllCommunityMembersIntoModel() throws Exception {
		Collection<CommunityMember> allCommunityMember = Arrays.asList(communityMember, anotherCommunityMember);
		when(communityMemberRepo.findAll()).thenReturn(allCommunityMember);
		
		mvc.perform(get("/all-community-members")).andExpect(model().attribute("community-members", allCommunityMember));
	}
	
		
			
		
	}		
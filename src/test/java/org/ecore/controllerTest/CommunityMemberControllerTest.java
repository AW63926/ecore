package org.ecore.controllerTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.ecore.controller.CommunityMemberContoller;
import org.ecore.model.CommunityMember;
import org.ecore.notFoundException.CommunityMembersNotFoundException;
import org.ecore.repository.CommunityMemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;



public class CommunityMemberControllerTest {
	
	@InjectMocks
	CommunityMemberContoller underTest;
	
	@Mock
	CommunityMemberRepository communityMemberRepo;
	
	@Mock
	CommunityMember communityMember;
	
	@Mock
	CommunityMember anotherCommunityMember;
	
	@Mock
	Model model;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddToModel() throws CommunityMembersNotFoundException {
		long arbitraryCommunityMemberId = 1;
		
		when(communityMemberRepo.findById(arbitraryCommunityMemberId)).thenReturn(Optional.of(communityMember));
		underTest.findOneCommunityMember(arbitraryCommunityMemberId, model);
		verify(model).addAttribute("communitymember", communityMember);
		
	}
	
	@Test
	public void shouldAddAllCommunityMembersToModel() {
		Collection<CommunityMember> allCommunityMembers =Arrays.asList(communityMember, anotherCommunityMember);
		System.out.println(allCommunityMembers);
		when(communityMemberRepo.findAll()).thenReturn(allCommunityMembers);
		underTest.FindAllCommunityMembers(model);
		verify(model).addAttribute("communitymembers", allCommunityMembers);
		
	}
	
	@Test
	public void shouldAddAdditionalCommunityMemberToModel() {
		String name = "name";
		String email= "email";
		underTest.addCommunityMember(name, email);
		ArgumentCaptor<CommunityMember> memberArgument = ArgumentCaptor.forClass(CommunityMember.class);
		verify(communityMemberRepo).save(memberArgument.capture());
		assertEquals("name", memberArgument.getValue().getName());
		
	}
	
	@Test public void shouldDeleteCommunityMemberByName() {
		String memberName = communityMember.getName();
		when(communityMemberRepo.findByNameIgnoreCaseLike(memberName)).thenReturn(communityMember);
		underTest.deleteCommunityMemberByName(memberName);
		verify(communityMemberRepo).delete(communityMember);
	
	}
}

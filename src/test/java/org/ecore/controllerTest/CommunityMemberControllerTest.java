package org.ecore.controllerTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.ecore.controller.CommunityMemberContoller;
import org.ecore.model.CommunityMember;
import org.ecore.notFoundException.CommunityMembersNotFoundException;
import org.ecore.repository.CommunityMemberRepository;
import org.junit.Before;
import org.junit.Test;
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
		
		
	}
	
	@Test
	public void shouldAddAdditionalCommunityMemberToModel() {
		String name = "name";
		String email= "email";
		CommunityMember communityMember = communityMemberRepo.save(new CommunityMember(name, email));
		underTest.addCommunityMember(name, email);
		when(communityMemberRepo.save(communityMember)).thenReturn(communityMember);
	}
	
	@Test public void shouldDeleteCommunityMemberByName() {
		String memberName = communityMember.getName();
		when(communityMemberRepo.findByNameIgnoreCaseLike(memberName)).thenReturn(communityMember);
		underTest.deleteCommunityMemberByName(memberName);
		verify(communityMemberRepo).delete(communityMember);
	
	}
}

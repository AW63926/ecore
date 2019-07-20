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
		verify(model).addAttribute("communitymembers", communityMember);
		
	}
	
	@Test
	public void shouldAddAllCommunityMembersToModel() {
		Collection<CommunityMember> allCommunityMembers =Arrays.asList(communityMember, anotherCommunityMember);
		System.out.println(allCommunityMembers);
		when(communityMemberRepo.findAll()).thenReturn(allCommunityMembers);
		underTest.FindAllCommunityMembers(model);
		
		
	}
}

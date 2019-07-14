package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.CommunityMember;
import org.ecore.repository.CommunityMemberRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/community-members")
public class CommunityMemberRestController {
	
	@Resource
	private CommunityMemberRepository communityMemberRepo;
	
	@RequestMapping("")
		public Iterable<CommunityMember> findAllCommunityMembers(){
			return communityMemberRepo.findAll();
	}
	
	@RequestMapping("/{id}")
	public Optional<CommunityMember> findOneCommunityMember(@PathVariable long id) {
		return communityMemberRepo.findById(id);
	}
	

}

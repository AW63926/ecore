package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.CommunityMember;
import org.ecore.notFoundException.CommunityMembersNotFoundException;
import org.ecore.repository.CommunityMemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommunityMemberContoller {

	@Resource
	CommunityMemberRepository communityMemberRepo;

	@RequestMapping("/community-member")
	public String findOneCommunityMember(@RequestParam(value = "id") long id, Model model)
			throws CommunityMembersNotFoundException {
		Optional<CommunityMember> communityMember = communityMemberRepo.findById(id);

		if (communityMember.isPresent()) {
			model.addAttribute("communitymember", communityMember.get());
			return "community-member";

		}

		throw new CommunityMembersNotFoundException();

	}

	@RequestMapping("/all-community-members")
	public String FindAllCommunityMembers(Model model) {
		model.addAttribute("communitymembers", communityMemberRepo.findAll());
		return "all-community-members";

	}

	@RequestMapping("/add-community-member")
	public String addCommunityMember(String name, String email) {
		CommunityMember communityMember = communityMemberRepo.findByNameIgnoreCaseLike(name);
		
		if(communityMember == null) {
			communityMember = new CommunityMember(name, email);
			communityMemberRepo.save(communityMember);
		}
				return "redirect:/all-community-members";
		
	}
	@RequestMapping("/delete-community-member")
	public String deleteCommunityMemberByName(String name) {
		CommunityMember communityMember = communityMemberRepo.findByNameIgnoreCaseLike(name);
		
		if(communityMember != null) {
			communityMemberRepo.delete(communityMember);
		}
		return "redirect:/all-community-members";
	}

}

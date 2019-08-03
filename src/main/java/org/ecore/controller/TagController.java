package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Tag;
import org.ecore.notFoundException.TagNotFoundException;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TagController {

	@Resource
	TagRepository tagRepo;
	
	@Resource
	NeedRepository needRepo;
	
	@RequestMapping("/tag")
	public String findOneTag(@RequestParam(value = "id") long id, Model model) throws TagNotFoundException {
		Optional<Tag> tag =tagRepo.findById(id);
		
		if(tag.isPresent()) {
			model.addAttribute("tags", tag.get());
			model.addAttribute("needs", tag.get().getNeeds());
			return "tag";
		}
		throw new TagNotFoundException();
		
	}
		
	@RequestMapping("/all-tags")
	public String findAllTags(Model model) {
		model.addAttribute("tags", tagRepo.findAll());
		model.addAttribute("needs", needRepo.findAll());
		return("all-tags");
	}
	
}
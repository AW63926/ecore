package org.ecore.controller;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/needs")

public class NeedRestController {
	@Resource
	private NeedRepository needRepo;
	
	@Resource
	private TagRepository tagRepo;
	
	@RequestMapping("")
	public Iterable<Need>findAllNeeds(){
		return needRepo.findAll();
	}
	
	@RequestMapping("/{id}")
	public Optional<Need>findOneNeed(@PathVariable long id){
		return needRepo.findById(id);
	}
	
	@RequestMapping("/tags/{tagName}")
	public Collection<Need>findAllNeedsByTag(@PathVariable(value = "tagName") String tagName){
		Tag tag = tagRepo.findByNameIgnoreCaseLike(tagName);
		return needRepo.findByTagsContains(tag);
	}

}

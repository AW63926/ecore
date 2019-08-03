package org.ecore.controller;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Material;
import org.ecore.model.Tag;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.TagRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
public class MaterialRestController {

	
	@Resource
	private MaterialRepository materialRepo;
	
	@Resource
	private TagRepository tagRepo;
	
	@RequestMapping("")
	public Iterable<Material> returnAllMaterials() {
		
		return materialRepo.findAll();
	}
	
	@RequestMapping("/{id}")
	public Optional<Material> findOneMaterial(@PathVariable long id){
		
		return materialRepo.findById(id);
	}
	
	@RequestMapping("/tags/{tagName}")
	public Collection<Material> findAllMaterialsbyTag(@PathVariable(value = "tagName") String tagName) {
		Tag tag = tagRepo.findByNameIgnoreCaseLike(tagName);
		
		return materialRepo.findByTagsContains(tag);
	}
}

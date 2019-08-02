package org.ecore.controller;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Material;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.notFoundException.MaterialNotFoundException;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MaterialController {

	@Resource
	MaterialRepository materialRepo;
	
	@Resource
	TagRepository tagRepo;
	
	@Resource
	TeacherRepository teacherRepo;

	@RequestMapping("/material")
	public String findOneMaterial(@RequestParam(value = "id") long id, Model model) throws MaterialNotFoundException {

		Optional<Material> material = materialRepo.findById(id);
		if (material.isPresent()) {
			model.addAttribute("material", material.get());
			return "material";
		}

		throw new MaterialNotFoundException();
	}

	@RequestMapping("/all-materials")
	public String findAllMaterials(Model model) {
		model.addAttribute("materials", materialRepo.findAll());
		return "all-materials";

	}

	@RequestMapping("/add-material")
	public String addMaterial(String name, int quantity, String descMaterial, String teacherName) {
		
		Teacher teacher = teacherRepo.findByNameIgnoreCaseLike(teacherName);
		
		Material material = materialRepo.findByName(name);

		if (material == null) {
			material = materialRepo.save(new Material(name, quantity, descMaterial, teacher));

		}

		return "redirect:/all-materials";
	}

	@RequestMapping("/delete-material")
	public String deleteMaterialById(Long id) {

		materialRepo.deleteById(id);

		return "redirect:/all-materials";

	}
	
	@RequestMapping("/tags/{tagName}")
	public Collection<Material> findAllNeedsByTag(@PathVariable(value = "tagName") String tagName){
		Tag tag = tagRepo.findByNameIgnoreCaseLike(tagName);
		return materialRepo.findByTagsContains(tag);
	}
	
	@RequestMapping(path = "/tags/add/{tagName}/{id}", method = RequestMethod.POST)
	public String addTagToMaterial(@PathVariable String tagName, @PathVariable Long id, Model model) {
		Tag tagToAdd = tagRepo.findByName(tagName);
		if(tagToAdd == null) {
			tagToAdd = new Tag(tagName);
			tagRepo.save(tagToAdd);
		}
		
		Material materialToAddTo = materialRepo.findById(id).get();
	
		materialToAddTo.addTag(tagToAdd);
		materialRepo.save(materialToAddTo);
		
		model.addAttribute("material", materialToAddTo);
		
		return "partial/tags-list-added";
	}
	
	@RequestMapping(path = "/tags/remove/{tagId}/{materialId}", method = RequestMethod.POST)
	public String removeTagFromMaterial(@PathVariable Long tagId, @PathVariable Long needId, Model model) {
		Tag tagToRemove = tagRepo.findById(tagId).get();
		
		Material materialToRemoveFrom = materialRepo.findById(needId).get();
		
		materialToRemoveFrom.removeTag(tagToRemove);
		materialRepo.save(materialToRemoveFrom);
		model.addAttribute("need", materialToRemoveFrom);
		
		return "partial/tags-list-removed";
	}
}

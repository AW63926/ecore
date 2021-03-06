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

	@RequestMapping("/single-material")
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
	public String addMaterial(String name, int quantity, String descMaterial, String teacherName, String tagName) {
		
		Teacher teacher = teacherRepo.findByNameIgnoreCaseLike(teacherName);
		
		Material material = materialRepo.findByName(name);
		Tag tag = tagRepo.findByNameIgnoreCaseLike(tagName);
		
		
			if(tag == null) {
				tag = tagRepo.save(new Tag(tagName));
			}
		
		if (material == null) {
			material = materialRepo.save(new Material(name, quantity, descMaterial, teacher, tag));

		}
		
		long id = teacher.getId();
		return "redirect:/teacher?id=" + id;
	}

	@RequestMapping("/delete-material")
	public String deleteMaterialById(Long id) {
		Optional<Material> materialToReturn = materialRepo.findById(id);
		Material material = materialToReturn.get();
		Teacher teacher = teacherRepo.findByMaterials(material);
		Long teacherId = teacher.getId();
		materialRepo.deleteById(id);
		
		

		return "redirect:/teacher?id=" + teacherId;

	}
	
	@RequestMapping("/tags/{tagName}")
	public Collection<Material> findAllNeedsByTag(@PathVariable(value = "tagName") String tagName){
		Tag tag = tagRepo.findByNameIgnoreCaseLike(tagName);
		return materialRepo.findByTagsContains(tag);
	}
	
	@RequestMapping(path = "/materials/tags/{tagName}/{materialId}", method = RequestMethod.POST)
	public String addTagToMaterial(@PathVariable String tagName, @PathVariable Long materialId, Model model) {
		Tag tagToAdd = tagRepo.findByName(tagName);
		if(tagToAdd == null) {
			tagToAdd = new Tag(tagName);
			tagRepo.save(tagToAdd);
		}
		
		Material materialToAddTo = materialRepo.findById(materialId).get();
	
		materialToAddTo.addTag(tagToAdd);
		materialRepo.save(materialToAddTo);
		
		model.addAttribute("material", materialToAddTo);
		
		return "partial/tags-list-materials-added";
	}
	
	@RequestMapping(path = "/materials/tags/remove/{tagId}/{materialId}", method = RequestMethod.POST)
	public String removeTagFromMaterial(@PathVariable Long tagId, @PathVariable Long materialId, Model model) {
		Tag tagToRemove = tagRepo.findById(tagId).get();
		
		Material materialToRemoveFrom = materialRepo.findById(materialId).get();
		
		materialToRemoveFrom.removeTag(tagToRemove);
		materialRepo.save(materialToRemoveFrom);
		model.addAttribute("material", materialToRemoveFrom);
		
		return "partial/tags-list-materials-removed";
	}
}
package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Need;
import org.ecore.model.Tag;
import org.ecore.model.Teacher;
import org.ecore.notFoundException.NeedNotFoundException;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.TagRepository;
import org.ecore.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NeedController {

	@Resource
	NeedRepository needRepo;

	@Resource
	TagRepository tagRepo;

	@Resource
	TeacherRepository teacherRepo;

	@RequestMapping("/need")
	public String findOneNeed(@RequestParam(value = "id") long id, Model model) throws NeedNotFoundException {
		Optional<Need> need = needRepo.findById(id);

		if (need.isPresent()) {
			model.addAttribute("needs", need.get());
			return "need";
		}
		throw new NeedNotFoundException();
	}

	@RequestMapping("/all-needs")
	public String findAllNeeds(Model model) {
		model.addAttribute("needs", needRepo.findAll());
		return ("all-needs");

	}

	@RequestMapping("/delete-need")
	public String deleteNeedByName(String needName) {
		if (needRepo.findByName(needName) != null) {
			Need deletedNeed = needRepo.findByName(needName);
			needRepo.delete(deletedNeed);
		}
		return "redirect:/all-needs";

	}

	@RequestMapping("/add-need")
	public String addNeed(String needName, int needQuantity, String needDescription, String teacherName,
			String tagName) {
		Teacher teacher = teacherRepo.findByNameIgnoreCaseLike(teacherName);

		Need newNeed = needRepo.findByNameIgnoreCaseLike(needName);
		Tag tag = tagRepo.findByNameIgnoreCaseLike(tagName);

		if (tag == null) {
			tag = new Tag(tagName);
			tagRepo.save(tag);
		}

		if (newNeed == null) {
			newNeed = new Need(needName, needQuantity, needDescription, teacher, tag);
			needRepo.save(newNeed);

		}
		long id = newNeed.getId();
		return "redirect:/need?id=" + id;

	}

	@RequestMapping("/del-need")
	public String deleteNeedById(Long needId) {

		needRepo.deleteById(needId);

		return "redirect:/all-needs";

	}

	@RequestMapping(path = "/tags/{tagName}/{id}", method = RequestMethod.POST)
	public String addTagToNeed(@PathVariable String tagName, @PathVariable Long id, Model model) {
		Tag tagToAdd = tagRepo.findByName(tagName);
		if (tagToAdd == null) {
			tagToAdd = new Tag(tagName);
			tagRepo.save(tagToAdd);
		}

		Need needToAddTo = needRepo.findById(id).get();

		needToAddTo.addTag(tagToAdd);
		needRepo.save(needToAddTo);

		model.addAttribute("need", needToAddTo);

		return "partial/tags-list-added";
	}

	@RequestMapping(path = "/tags/remove/{tagId}/{needId}", method = RequestMethod.POST)
	public String removeTagFromNeed(@PathVariable Long tagId, @PathVariable Long needId, Model model) {
		Tag tagToRemove = tagRepo.findById(tagId).get();

		Need needToRemoveFrom = needRepo.findById(needId).get();

		needToRemoveFrom.removeTag(tagToRemove);
		needRepo.save(needToRemoveFrom);
		model.addAttribute("need", needToRemoveFrom);

		return "partial/tags-list-removed";
	}

}

package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Need;
import org.ecore.notFoundException.NeedNotFoundException;
import org.ecore.repository.NeedRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NeedController {

	@Resource
	NeedRepository needRepo;

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
	public String addNeed(String needName, int i, String descNeed) {
		Need newNeed = needRepo.findByName(needName);

		if (newNeed == null) {
			newNeed = new Need(needName, i, descNeed);
		}
		return "redirect:/all-needs";

	}

	@RequestMapping("/del-need")
	public String deleteNeedById(Long needId) {

		needRepo.deleteById(needId);

		return "redirect:/all-needs";

	}

}

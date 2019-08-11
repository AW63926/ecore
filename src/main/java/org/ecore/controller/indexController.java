package org.ecore.controller;

import javax.annotation.Resource;

import org.ecore.repository.NeedRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

	@Resource
	NeedRepository needRepo;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		model.addAttribute("needs", needRepo.findAll());
		return "index";
	}
	
	@RequestMapping("/indexcontent")
	public String showIndexContent(Model model) {
		model.addAttribute("needs", needRepo.findAll());
		return "indexcontent";
	}
	
	
}

package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.School;
import org.ecore.notFoundException.SchoolNotFoundException;
import org.ecore.repository.SchoolRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SchoolController {
	
	@Resource
	SchoolRepository schoolRepo;
	
	@RequestMapping("/school")
	public String findOneSchool(@RequestParam(value="id")long id, Model model) throws SchoolNotFoundException {
		Optional<School> school = schoolRepo.findById(id);
		
		if(school.isPresent()) {
			model.addAttribute("schools", school.get());
			return "school";
		}
		
		throw new SchoolNotFoundException();
		
	}

	@RequestMapping("/show-schools")
	public String findAllSchools(Model model) {
		model.addAttribute("schools", schoolRepo.findAll());
		return ("schools");
		
	}

}

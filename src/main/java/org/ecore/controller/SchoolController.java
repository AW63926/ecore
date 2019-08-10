package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Material;
import org.ecore.model.Need;
import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.ecore.notFoundException.SchoolNotFoundException;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.SchoolRepository;
import org.ecore.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SchoolController {

	@Resource
	SchoolRepository schoolRepo;

	@Resource
	TeacherRepository teacherRepo;

	@Resource
	MaterialRepository materialRepo;
	
	@Resource
	NeedRepository needRepo;

	@RequestMapping("/school")
	public String findOneSchool(@RequestParam(value = "id") long id, Model model) throws SchoolNotFoundException {
		Optional<School> school = schoolRepo.findById(id);

		if (school.isPresent()) {
			model.addAttribute("schools", school.get());
			return "school";
		}

		throw new SchoolNotFoundException();

	}

	@RequestMapping("/all-schools")
	public String findAllSchools(Model model) {
		model.addAttribute("schools", schoolRepo.findAll());
		return ("all-schools");

	}

	@RequestMapping("/add-school")
	public String addSchool(String name, String district, String address, String mapUrl) {
		School school = schoolRepo.findByNameIgnoreCaseLike(name);

		if (school == null) {
			school = schoolRepo.save(new School(name, district, address, mapUrl));

		}
		return "redirect:/all-schools";
	}

	@RequestMapping("/delete-school")
	public String deleteSchoolById(Long schoolId) {
		Optional<School> school = schoolRepo.findById(schoolId);
		School schoolToRemove = school.get();;

		for (Teacher teacher : schoolToRemove.getTeachers()) {
			for (Material material : teacher.getMaterials()) {
				materialRepo.delete(material);
			}
			for(Need need : teacher.getNeeds()) {
				needRepo.delete(need);
			}

			teacherRepo.delete(teacher);
		}

		schoolRepo.delete(schoolToRemove);

		return "redirect:/all-schools";
	}
}

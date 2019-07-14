package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Teacher;
import org.ecore.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeacherController {
	
	@Resource
	TeacherRepository teacherRepo;

	@RequestMapping("/teachers")
	public String findOneTeacher(@RequestParam(value="id")long id, Model model) throws TeacherNotFoundException {
		Optional<Teacher> teacher = teacherRepo.findById(id);
		
		if(teacher.isPresent()) {
			model.addAttribute("teachers", teacher.get());
			return "teacher";
		}
		
		throw new TeacherNotFoundException();
	}

	@RequestMapping("/show-teachers")
	public String findAllTeachers(Model model) {
		model.addAttribute("teachers", teacherRepo.findAll());
		return ("teachers");
		
	}
	
	

}

package org.ecore.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.ecore.model.Material;
import org.ecore.model.Need;
import org.ecore.model.School;
import org.ecore.model.Teacher;
import org.ecore.notFoundException.TeacherNotFoundException;
import org.ecore.repository.MaterialRepository;
import org.ecore.repository.NeedRepository;
import org.ecore.repository.SchoolRepository;
import org.ecore.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeacherController {

	@Resource
	TeacherRepository teacherRepo;

	@Resource
	SchoolRepository schoolRepo;
	
	@Resource
	NeedRepository needRepo;
	
	@Resource
	MaterialRepository materialRepo;
	
	@RequestMapping("/teacher")
	public String findOneTeacher(@RequestParam(value = "id") long id, Model model) throws TeacherNotFoundException {
		Optional<Teacher> teacher = teacherRepo.findById(id);

		if (teacher.isPresent()) {
			model.addAttribute("teachers", teacher.get());
			return "teacher";
		}

		throw new TeacherNotFoundException();
	}

	@RequestMapping("/all-teachers")
	public String findAllTeachers(Model model) {
		model.addAttribute("teachers", teacherRepo.findAll());
		return ("all-teachers");

	}

	@RequestMapping("/add-teacher")
	public String addTeacher(String name, String specialty, String school, String email) {
		School school1 = schoolRepo.findByNameIgnoreCaseLike(school);

		if (school == null) {
			String schoolDistrict = "dist";
			String schoolAddress = "address";
			String schoolMapUrl = "url";
			school1 = new School(school, schoolDistrict, schoolAddress, schoolMapUrl);
			schoolRepo.save(school1);
		}

		Teacher newTeacher = teacherRepo.findByNameIgnoreCaseLike(name);
		if (newTeacher == null) {
			newTeacher = new Teacher(name, specialty, school1, email);
			teacherRepo.save(newTeacher);
		}
		return "redirect:/all-teachers";
	}

	@RequestMapping("/delete-teacher")
	public String deleteTeacherByName(String teacherName) {
		Teacher foundTeacher = teacherRepo.findByNameIgnoreCaseLike(teacherName);
		for(Need need : foundTeacher.getNeeds()) {
			needRepo.delete(need);
		}
		
		for(Material material : foundTeacher.getMaterials()) {
			materialRepo.delete(material);
		}
			teacherRepo.delete(foundTeacher);
		
		return "redirect:/all-teachers";
	}

	@RequestMapping("/del-teacher")
	public String deleteTeacherById(Long teacherId) {
		Optional<Teacher> foundTeacherResult = teacherRepo.findById(teacherId);
		Teacher foundTeacher = foundTeacherResult.get();
		for(Need need : foundTeacher.getNeeds()) {
			needRepo.delete(need);
		}
		
		for(Material material : foundTeacher.getMaterials()) {
			materialRepo.delete(material);
		}
		teacherRepo.delete(foundTeacher);

		return "redirect:/all-teachers";
	}

	@RequestMapping("/teacher-login")
	public String showLogin() {

		return "teacher-login";
	}

	@RequestMapping("/login-submit")
	public String loginSubmit(String emailAddress) {
		Teacher teacher = teacherRepo.findByEmail(emailAddress);
		Long teacherId = teacher.getId();

		return "redirect:/teacher?id=" + teacherId;

	}

	@RequestMapping("/teacher-signup")
	public String teacherSignup(String name, String specialty, String school, String email) {
		School school1 = schoolRepo.findByNameIgnoreCaseLike(school);

		if (school == null) {
			String schoolDistrict = "dist";
			String schoolAddress = "address";
			String schoolMapUrl = "url";
			school1 = new School(school, schoolDistrict, schoolAddress, schoolMapUrl);
			schoolRepo.save(school1);
		}
		
		Teacher newTeacher = teacherRepo.findByNameIgnoreCaseLike(name);
		if (newTeacher == null) {
			newTeacher = new Teacher(name, specialty, school1, email);
			teacherRepo.save(newTeacher);
		}
		
		Long teacherId = newTeacher.getId();
		return "redirect:/teacher?id=" + teacherId;

	}
	

}

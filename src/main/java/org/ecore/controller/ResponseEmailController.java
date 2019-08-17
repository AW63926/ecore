package org.ecore.controller;

import java.util.Optional;

import javax.mail.internet.MimeMessage;
import org.ecore.model.Teacher;
import org.ecore.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResponseEmailController {
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private TeacherRepository teacherRepo;

	@RequestMapping(value = "/responseemail")
	@ResponseBody
	public String home(Long id, String responderName, String email, String comment) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			Optional<Teacher> foundTeacher = teacherRepo.findById(id);
			Teacher teacher = foundTeacher.get();
			

			helper.setTo(teacher.getEmail());
			
			helper.setText(responderName + " has responded with: " + comment + "\tContact info is: " + email);
			helper.setSubject("Response to Resource Request");
			sender.send(message);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}
}
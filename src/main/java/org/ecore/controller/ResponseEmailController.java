package org.ecore.controller;

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
	public String home(String name, String responderName, String email, String comment) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			System.out.println("METHOD FIRING**************");
			Teacher teacher = teacherRepo.findByNameIgnoreCaseLike(name);
			System.out.println("TEACHER FOUND!!!!!!!!!!!!!!!!");

			helper.setTo(teacher.getEmail());
			System.out.println("TEACHER EMAIL ******************" + teacher.getEmail());
			helper.setText(responderName + " has responded with: " + comment + "\tContact info is: " + email);
			helper.setSubject("Response to Resource Request");
			sender.send(message);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}
}
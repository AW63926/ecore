package org.ecore.controller;

import javax.mail.internet.MimeMessage;

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

	@RequestMapping(value = "/responseemail")
	@ResponseBody
	String home() {
		try {
			sendEmail();
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}

	}

	private void sendEmail() throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setTo("ecoreproject2019@gmail.com");
		helper.setText("I want to help");
		helper.setSubject("Willing to help");

		sender.send(message);
	}

}

package org.ecore.model;


import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Need implements JavaMailSender{
	
	@GeneratedValue
	@Id
	private long id;
	
	private String name;
	private int quantity;

	@Lob
	public String descNeed;
	
	@JsonIgnore
	@ManyToOne
	private Teacher teacher;

	@JsonIgnore
	@ManyToMany
	private Collection<Tag> tags;

	public Need(String name, int quantity, String descNeed, Teacher teacher, Tag...tags) {
		this.name = name;
		this.quantity = quantity;
		this.descNeed = descNeed;
		this.teacher = teacher;
		this.tags = new HashSet<>(Arrays.asList(tags));

	}
	
	public Need() {
		
	}


	public long getId() {
		return id;
	}

	public Collection<Tag> getTags() {
		return tags;
	}


	public String getName() {
	return name;
	
}
	public int getQuantity() {
		return quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Need other = (Need) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void addTag(Tag tagToAdd) {
		tags.add(tagToAdd);		
	}
	
	public void removeTag(Tag tagToRemove) {
		tags.remove(tagToRemove);		
	}

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;

	String emailHost = "smtp.gmail.com";
	String emailPort = "587";// gmail's smtp port
	String fromUser = "gmail email here";// your gmail id
	String fromUserEmailPassword = "password here";
	String[] toEmails = { "ecoreproject2019@gmail.com" };

	public void setMailServerProperties() {
		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
	}

	public void createEmailMessage(String emailSubject, String emailBody)
			throws AddressException, MessagingException {
		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);
		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toEmails[i]));
		}
		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");// for a html email
		// emailMessage.setText(emailBody);// for a text email

	}

	public void sendEmail() throws AddressException, MessagingException {
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
	}

	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MimeMessage createMimeMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(MimeMessage mimeMessage) throws MailException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(MimeMessage... mimeMessages) throws MailException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
		// TODO Auto-generated method stub
		
	}
}


	

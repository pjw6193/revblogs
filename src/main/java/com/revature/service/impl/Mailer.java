package com.revature.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class Mailer {
	
	private Mailer(){
		throw new IllegalAccessError("Utility Class");
	}
	
	public static void sendMail(String newEmail, String newPassword) {
		
		Logger log = Logger.getRootLogger();
	
    	Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.host", "smtp.gmail.com");
    	props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("d.p.pgdr@gmail.com","danpickles1");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("d.p.pgdr@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(newEmail));
			message.setSubject("RevBlogs Registration (DO NOT REPLY)");
			message.setContent(makeHTML(newEmail, newPassword), "text/html");

			Transport.send(message);

		} catch (MessagingException e) {
			
			log.error(e);
		}
    }
	
	public static String makeHTML(String newEmail, String newPassword){
		
		String myHTML = new String(
				 
				 "<!DOCTYPE html>" 											 
			   + "<html>"
			   + "<body>"  		 											
			   + "<div>"
			   + "<h3>"
			   + 	"RevBlogs Registration!"
			   + "</h3><br/>"
			   + "<h5>"
			   + 	"Welcome, aboard"
			   + "</h5>"
			   + "<p>"
			   + 	"Thank you for registering with RevBlogs! "
			   + 	"I'm sure you'll blog your heart away; but "
			   + 	"first, you need to create your account."
			   + "</p><br/><br/>"
			   + "<p>"
			   + 	"Please access our website to get started: "
			   + 	"<a href=\"http://localhost:7001/revblogs/loginPage\">RevBlogs</a>"
			   + "</p><br/><br/>"
			   + "<p>"
			   + 	"Here are your credentials: <br/>"
			   + 	"Email: " + newEmail + "<br/>"
			   +    "Password: " + newPassword + "<br/>"
			   +    "<strong>Keep these safe and never share them with anyone!</strong>"
			   + "</p><br/><br/>"
			   + "<hr/>"
			   + "</div>"  	 
			   + "</body>" 		 
			   + "</html>"
		);
		
		return myHTML;
	}
}

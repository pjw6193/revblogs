package com.revature.service.impl;


import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.revature.service.Logging;

public class Mailer {
	private Mailer(){
		throw new IllegalAccessError("Utility Class");
	}
	public static void sendMail(String...emailInfo) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("revblogs@gmail.com","danpickles1");
				}
			});

		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("revblogs@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailInfo[0]));
			message.setSubject("Revature Blog Registration (DO NOT REPLY)");
			
			 // This mail has 2 part, the BODY and the embedded image
	         MimeMultipart multipart = new MimeMultipart("related");

	         // first part (the html)
	         BodyPart messageBodyPart = new MimeBodyPart();
	         if(emailInfo.length == 4){
	        	 String htmlText = "<!DOCTYPE html>"
	         				+ "<html>"
	         				+ 	"<body style=\"background-color: #F9F9F9;\">"
	         				+ 		"<table style=\"background-color: #FFFFFF;\" align=\"center\">"
	         				+ 			"<tr>"
	         				+ 				"<td width=\"10%\"></td>"
	         				+ 				"<td width=\"40%\"></td>"
	         				+ 				"<td width=\"50%\"><img style=\"display: block; margin: auto 0px auto 50%;\" src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\"></td>"
	         				+ 			"</tr>"
	         				+ 			"<tr>"
	         				+ 				"<td></td>"
	         				+ 				"<td colspan=\"2\">"
	         				+ 					"<h1>Hello"+emailInfo[2]+" "+emailInfo[3]+",</h1>"
	         				+ 					"<h2>Your password has been recently changed.</h2>"
	         				+ 					"<p>Here is your new password: <strong>"+emailInfo[1]+"</strong></p>"
	         				+ 					"<br /><br /><br /><br /><br />"
	         				+ 					"<img src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\">"
	         				+ 					"<p>"
	         				+ 						"<span style=\"color:gray;\">11730 Plaza America Dr. | Suite 205 | Reston, VA 20190 | </span>"
	         				+						"<a href=\"https://revature.com/\">http://revature.com</a>"
	         				+ 					"</p>"
	         				+ 				"</td>"
	         				+			"</tr>"
	         				+ 		"</table>"
	         				+ 	"</body>"
	         				+ "</html>";
	         }
	         else{
	        	 String htmlText = "<!DOCTYPE html>"
	         				+ "<html>"
	         				+ 	"<body style=\"background-color: #F9F9F9;\">"
	         				+ 		"<table style=\"background-color: #FFFFFF;\" align=\"center\">"
	         				+ 			"<tr>"
	         				+ 				"<td width=\"10%\"></td>"
	         				+ 				"<td width=\"40%\"></td>"
	         				+ 				"<td width=\"50%\"><img style=\"display: block; margin: auto 0px auto 50%;\" src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\"></td>"
	         				+ 			"</tr>"
	         				+ 			"<tr>"
	         				+ 				"<td></td>"
	         				+ 				"<td colspan=\"2\">"
	         				+ 					"<h1>Welcome New User,</h1>"
	         				+ 					"<h2>You have been invited to Revature Blogs</h2>"
	         				+ 					"<p>Your Username will be: <strong>"+emailInfo[0]+"</strong></p>"
	         				+ 					"<p>Here is your password: <strong>"+emailInfo[1]+"</strong></p>"
	         				+ 					"<p> Please proceed to <a href=\"http://localhost:7001/revblogs/loginPage\">http://blogs.pjw6193.tech</a> and login to change your password.</p>"
	         				+ 					"<br /><br /><br /><br /><br />"
	         				+ 					"<img src=\"http://blogs.pjw6193.tech/content/resources/img/rev-brand.png\">"
	         				+ 					"<p>"
	         				+ 						"<span style=\"color:gray;\">11730 Plaza America Dr. | Suite 205 | Reston, VA 20190 | </span>"
	         				+						"<a href=\"https://revature.com/\">http://revature.com</a>"
	         				+ 					"</p>"
	         				+ 				"</td>"
	         				+			"</tr>"
	         				+ 		"</table>"
	         				+ 	"</body>"
	         				+ "</html>";
	         }
	         messageBodyPart.setContent(htmlText, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);

	         // put everything together
	         message.setContent(multipart);
			
			Transport.send(message);

		} catch (MessagingException e) {
			Logging.error(e);
		}
    }

}
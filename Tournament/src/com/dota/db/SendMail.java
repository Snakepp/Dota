package com.dota.db;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMail {
 
	public void sendActivate(int userId, String toEmail) {
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
		 
				Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("doterossj@gmail.com","myPass4dota");
						}
					});
		 
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("doteros@dotaRoom.com"));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(toEmail));
					message.setSubject("Wlcome to Doteros San Jacinto");
					
					message.setContent
			         ("<b>Welcome to Doteros San jacinto</b> <br>to activate your account please follow the next link in order to succesfuly log in. <br><br> http://localhost:8080/Tournament/#!/activate?id="+userId, "text/html; charset=ISO-8859-1");
					
//					message.setText("Dear Mail Crawler," +
//							" No spam to my email, please!");
		 
					Transport.send(message);
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
	}
//	
//	public MimeBodyPart getMessage(){
//		MimeBodyPart body;
//		String message = ""
//				+ "Welcome to Doteros San jacinto, \n\n"
//				+ ""
//				+ "to activate your account please follow the next link in order to succesfuly log in. \n"
//				+ ""
//				+ "";
//		
//		
//		InternetHeaders headers = new InternetHeaders();
//		headers.addHeader("Content-type", "text/html; charset=UTF-8");
//		String html = "Test\n" + message + "\n<a href='http://test.com'>Test.com</a>";
//		try {
//			body = new MimeBodyPart(headers, html.getBytes("UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//		
//		
//		return body;
//	}
}

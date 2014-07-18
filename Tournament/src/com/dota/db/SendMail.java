package com.dota.db;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.dota.tournament.User;
import com.vaadin.ui.Notification;
 
public class SendMail {
 
	public void sendActivate(int userId, String toEmail, String URL) {
		if(!URL.contains("localhost")){
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
					message.setSubject("Welcome to Doteros San Jacinto");
					
					message.setContent
			         (getMessageToSend(URL, userId), "text/html; charset=ISO-8859-1");
					
		 
					Transport.send(message);
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
		}else{
			Notification.show("Testing send Message succesfully!");
		}
	}
	
	public String getMessageToSend(String URL,int userId){
		String message ="<b>Welcome to Doteros San jacinto</b>"+
						"<br>to activate your account please follow the next link in order to succesfuly log in. " + 
						"<br><br>" + URL + "Tournament/#!/activate?id="+userId;
		return message;
	}
	public String getMessageForgotPass(String newPass){
		String message ="<b>It looks that you forgot your password hee?</b>"+
				"<br>the password has been reseted to: " +newPass +  
				"<br><br>" + "Please try to change your password as soon as possible, <br>The DoterosSJ Team.";
		return message;
	}
	
	
	public void sendForgotPass(User user, String URL, String newPass){
		if(!URL.contains("localhost")){
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
						InternetAddress.parse(user.getEmail()));
				message.setSubject("Doteros San Jacinto Forgot Password");
				
				message.setContent
		         (getMessageForgotPass(newPass), "text/html; charset=ISO-8859-1");
				
	 
				Transport.send(message);
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}else{
			Notification.show("Testing send Message succesfully!");
		}
	}
	
}

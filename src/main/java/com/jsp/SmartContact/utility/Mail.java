package com.jsp.SmartContact.utility;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Mail {
	
	
	
	public void sendMail(int otp,String email,JavaMailSender mailSender) {
		
		String userName="llights309@gmail.com";
		
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		
		mailMessage.setFrom(userName);
		mailMessage.setSubject("OTP for Password Reset");
		mailMessage.setText("OTP is "+otp);
		mailMessage.setTo(email);
		
		mailSender.send(mailMessage);
		
		
	}
	
}

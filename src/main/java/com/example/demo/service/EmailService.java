package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{
	
	@Autowired
    public JavaMailSender emailSender;
 
    public void sendSimpleMessage() {

        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo("poyin311@gmail.com"); 
        message.setSubject("Hello Nora"); 
        message.setText("null");
        emailSender.send(message);

    }

}

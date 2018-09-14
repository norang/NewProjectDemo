package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.example.demo.service.LoginAttemptService;
import com.example.demo.service.UserService; 

@Component 
public class AuthenticationFailureListener 
     implements ApplicationListener<AuthenticationFailureBadCredentialsEvent>{ 

	@Autowired
    private LoginAttemptService loginAttemptService;
	
	@Autowired
	private UserService userService;
	

    private final Logger logger = LoggerFactory.getLogger(getClass()); 

    @Override 
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    	
    	WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
    	loginAttemptService.loginFailed(auth.getRemoteAddress());
    	userService.addFailedLoginAttempt(event.getAuthentication().getName());
    	
//    	logger.info("{}", event);
    } 
    
   
} 
package com.example.demo.security;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.model.UserAccessLog;
import com.example.demo.service.LoginAttemptService;
import com.example.demo.service.UserAccessLogService;
import com.example.demo.service.UserService; 

@Component 
public class AuthenticationFailureListener 
     implements ApplicationListener<AuthenticationFailureBadCredentialsEvent>{ 

	@Autowired
    private LoginAttemptService loginAttemptService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAccessLogService userAccessLogService;
	

    @Override 
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    	
    	WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
    	loginAttemptService.loginFailed(auth.getRemoteAddress());
    	userService.addFailedLoginAttempt(event.getAuthentication().getName());
    	
    	User user = userService.findByUsername(event.getAuthentication().getName());
    	
    	if (user != null)
    		userAccessLogService.save(new UserAccessLog(user, new Timestamp(event.getTimestamp()), auth.getRemoteAddress()));
    	
    	

    } 
    
   
} 
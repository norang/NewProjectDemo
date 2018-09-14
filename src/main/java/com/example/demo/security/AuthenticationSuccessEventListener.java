package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.example.demo.service.LoginAttemptService;
import com.example.demo.service.UserService;

@Component
public class AuthenticationSuccessEventListener
        implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Autowired
    private LoginAttemptService loginAttemptService;
	
	@Autowired
	private UserService userService;
 
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) 
          event.getAuthentication().getDetails();
         
        loginAttemptService.loginSucceeded(auth.getRemoteAddress());
        userService.resetFailedLoginAttempt(event.getAuthentication().getName());
    }
}

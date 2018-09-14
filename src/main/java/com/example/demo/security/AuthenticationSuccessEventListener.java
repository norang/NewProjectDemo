package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.example.demo.service.LoginAttemptService;

@Component
public class AuthenticationSuccessEventListener
        implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Autowired
    private LoginAttemptService loginAttemptService;
 
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) 
          e.getAuthentication().getDetails();
         
        loginAttemptService.loginSucceeded(auth.getRemoteAddress());
    }
}

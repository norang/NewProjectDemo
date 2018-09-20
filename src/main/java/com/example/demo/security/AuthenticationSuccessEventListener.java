package com.example.demo.security;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.model.UserAccessLog;
import com.example.demo.service.LoginAttemptService;
import com.example.demo.service.UserAccessLogService;
import com.example.demo.service.UserService;
import com.example.demo.util.CommonUtil;

@Component
public class AuthenticationSuccessEventListener
        implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Autowired
    private LoginAttemptService loginAttemptService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAccessLogService userAccessLogService;
 
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) 
          event.getAuthentication().getDetails();
         
        loginAttemptService.loginSucceeded(auth.getRemoteAddress());
        userService.resetFailedLoginAttempt(event.getAuthentication().getName());
        
        User user = userService.findByUsername(event.getAuthentication().getName());
    	if (user != null)
    		userAccessLogService.save(new UserAccessLog(user, new Timestamp(event.getTimestamp()), auth.getRemoteAddress(), CommonUtil.SUCCESS));
    }
}

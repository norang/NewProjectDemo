package com.example.demo.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.demo.util.CommonUtil;

@Component
public class EventSendingAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements ApplicationEventPublisherAware {
	
	protected ApplicationEventPublisher eventPublisher;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
	public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request,
                       javax.servlet.http.HttpServletResponse response,
                       AuthenticationException exception)
                         throws IOException,
                                javax.servlet.ServletException {
    	
    	super.setDefaultFailureUrl("/login?error");
        // use eventPublisher to publish the event according to exception
        super.onAuthenticationFailure(request, response, exception);
        
        
        String[] message = exception.getMessage().split(CommonUtil.DELIMITER);

        if (CommonUtil.ACCOUNT_STATUS_BLOCKED_IP.equalsIgnoreCase(message[0]) || 
        		CommonUtil.ACCOUNT_STATUS_BLOCKED_ACCOUNT.equalsIgnoreCase(message[0])) {
        	logger.info(exception.getMessage());
        }
        
        if (exception instanceof UsernameNotFoundException) {
        	logger.info(exception.getMessage());
        }
        
        
    
    }

}

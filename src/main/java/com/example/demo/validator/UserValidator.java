package com.example.demo.validator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.CommonUtil;

@Component

public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messagesource;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }


        if(Boolean.parseBoolean(messagesource.getMessage("enablePasswordPolicy", null, null))) 
        	isPasswordValid(user, errors);

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

    }
    
    public void isPasswordValid(User user, Errors errors) {
    	String password = user.getPassword();
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    	
    	if (user.getPassword().equalsIgnoreCase(user.getUsername())) {
            errors.rejectValue("password", "Username.userForm.password");
        }
    	
    	if (password.length() < 8 || password.length() > 32)
    		errors.rejectValue("password", "Size.userForm.password");
    	
    	if (password.equals(password.toLowerCase()))
    		errors.rejectValue("password", "LowerCase.userForm.password");
    	
    	if (password.equals(password.toUpperCase()))
    		errors.rejectValue("password", "UpperCase.userForm.password");
    	
    	if (password.matches("[A-Za-z0-9 ]*"))
    		errors.rejectValue("password", "Symbol.userForm.password");
    		
    	if (!CommonUtil.stringContainsNumber(password))
    		errors.rejectValue("password", "Number.userForm.password");
    }
}

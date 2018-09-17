package com.example.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NumberFormatException.class)
	public final ModelAndView handleNumberFormatException(Model model, Exception ex, WebRequest request) {
		model.addAttribute("message", ex.getClass());
		return new ModelAndView("/error/error-500");
	}
	
	@ExceptionHandler(Exception.class)
	  public final ModelAndView handleAllException(Model model, Exception ex, WebRequest request) {
			model.addAttribute("message", ex.getClass());
			ex.printStackTrace();
		    return new ModelAndView("/error/error-500");
	}



}
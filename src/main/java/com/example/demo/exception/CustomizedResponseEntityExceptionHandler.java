package com.example.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

//  @ExceptionHandler(Exception.class)
//  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
//    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getClass().getName(),
//        request.getDescription(false));
//    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//  }
	
	
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

  
  
//  @ExceptionHandler(AccessDeniedException.class)
//  public final ModelAndView handleAccessDeniedException(Exception ex, WebRequest request) {
//	    return new ModelAndView("error-401");
//	  }

//  @ExceptionHandler(NumberFormatException.class)
//  public final ResponseEntity<ErrorDetails> handleNumberFormatException(NumberFormatException ex, WebRequest request) {
//    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getCause() + " " + ex.getMessage(),
//        request.getDescription(false));
//    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//  }
  


}
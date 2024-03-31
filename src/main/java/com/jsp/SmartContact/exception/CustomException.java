package com.jsp.SmartContact.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jsp.SmartContact.entity.Message;

@ControllerAdvice
public class CustomException {
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public String adminException(Model model) {
		model.addAttribute("adminexception", new Message("Authorization Denied", "alert-danger"));
		return "login";
	}
	
}

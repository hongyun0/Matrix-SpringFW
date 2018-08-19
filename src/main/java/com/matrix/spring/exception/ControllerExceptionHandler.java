package com.matrix.spring.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest request, Model model, Exception exception) throws Exception {
		log.warn("WARN! {} : '{}' [{}]", exception.getClass().getName(), exception.getMessage(),
				exception.getStackTrace()[0]);
		String backPage = request.getHeader("Referer");
		backPage.replaceFirst(request.getContextPath() + "/", "");
		model.addAttribute("backPage", backPage);
		return "error";
	}
}

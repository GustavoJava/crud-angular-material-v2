package com.loiane.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.loiane.exception.RecordNotFoundException;

@RestControllerAdvice
public class AplicationControllerAdvice {

	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleNotFoundException(RecordNotFoundException ex) {
		return ex.getMessage();
	}

}

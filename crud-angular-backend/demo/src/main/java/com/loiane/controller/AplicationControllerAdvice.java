package com.loiane.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.loiane.exception.RecordNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class AplicationControllerAdvice {

	/**
	 * buscar um id inexistente
	 * 
	 * Ex.: findById(0)
	 * 
	 */
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleNotFoundException(RecordNotFoundException ex) {
		return ex.getMessage();
	}

	/**
	 * campos vazios ao fazer um Post.
	 * 
	 * Ex.: name = ""
	 * 
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + " " + error.getDefaultMessage())
				.reduce("", (acc, error) -> acc + error + "\n");
	}

	/**
	 * buscar um id negativo
	 * 
	 * Ex.: findById(-1)
	 * 
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public String handleConstraintViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().stream().map(error -> error.getPropertyPath() + " " + error.getMessage())
				.reduce("", (acc, error) -> acc + error + "\n");
	}

	/**
	 * buscar um id String
	 * 
	 * Ex.: findById("abc")
	 * 
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		if (ex != null && ex.getRequiredType() != null) {
			String type = ex.getRequiredType().getName();
			String[] typeParts = type.split("\\.");
			String typeName = typeParts[typeParts.length - 1];			
			return ex.getName() + " dever ser do tipo " + typeName;
		}
		
		return "Tipo de argumento não válido";
	}

}

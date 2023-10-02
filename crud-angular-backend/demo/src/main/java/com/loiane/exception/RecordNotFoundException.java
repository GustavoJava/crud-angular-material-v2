package com.loiane.exception;

public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(Long id) {
		super(String.format("Registro com Id: %d não encontrado ", id));
	}
	

}

package com.rvk.mtbs.exception;

public class InvalidResetCodeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidResetCodeException(String message) {
		super(message);
	}
}
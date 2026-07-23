package com.rvk.mtbs.exception;

public class TheaterNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TheaterNotFoundException(String message) {
		super(message);
	}

}

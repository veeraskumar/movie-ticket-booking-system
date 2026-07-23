package com.rvk.mtbs.exception;

public class InvalidSeatConfigurationException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidSeatConfigurationException(String message) {
		super(message);
	}

}

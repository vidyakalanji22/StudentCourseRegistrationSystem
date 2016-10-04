package com.scr.exception;

public class ResourceAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -811151671394379527L;

	public ResourceAlreadyExistsException() {
		// TODO Auto-generated constructor stub
	}
	
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

}

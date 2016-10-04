package com.scr.exception;

public class ConstrainViolationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1330095069982826210L;

	public ConstrainViolationException() {
		// TODO Auto-generated constructor stub
	}
	
	public ConstrainViolationException(String message){
		super(message);
	}

}

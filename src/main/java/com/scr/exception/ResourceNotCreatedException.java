package com.scr.exception;

public class ResourceNotCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7853612617021322601L;

	public ResourceNotCreatedException() {
	}

	public ResourceNotCreatedException(String message) {
		super(message);
	}

}

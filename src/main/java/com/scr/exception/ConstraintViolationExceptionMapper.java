package com.scr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.scr.vo.ErrorMessage;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstrainViolationException> {

	public ConstraintViolationExceptionMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response toResponse(ConstrainViolationException exception) {
		ErrorMessage errorMessage = new ErrorMessage(400, exception.getMessage());
		return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
	}

}

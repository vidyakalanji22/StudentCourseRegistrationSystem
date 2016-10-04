package com.scr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.scr.vo.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<GenericException> {

	public GenericExceptionMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response toResponse(GenericException e) {
		ErrorMessage errorMessage = new ErrorMessage(500, e.getMessage());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

}

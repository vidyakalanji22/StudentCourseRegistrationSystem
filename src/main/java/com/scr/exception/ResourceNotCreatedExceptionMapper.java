package com.scr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.scr.vo.ErrorMessage;

@Provider
public class ResourceNotCreatedExceptionMapper implements ExceptionMapper<ResourceNotCreatedException> {

	public ResourceNotCreatedExceptionMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response toResponse(ResourceNotCreatedException e) {
		ErrorMessage errorMessage = new ErrorMessage(400, e.getMessage());
		return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
	}

}

package com.scr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.scr.vo.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	public DataNotFoundExceptionMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response toResponse(DataNotFoundException e) {
		ErrorMessage errorMessage = new ErrorMessage(404, e.getMessage());
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}

}

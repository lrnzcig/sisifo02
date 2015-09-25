package com.sisifo.almadraba_server;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;
import com.sisifo.almadraba_server.exception.AlmadrabaBaseException;

public class AlmadrabaExceptionMapper implements ExceptionMapper<AlmadrabaBaseException> {

	@Override
	public Response toResponse(AlmadrabaBaseException exception) {
		if (exception instanceof AlmadrabaAuthenticationException) {
			return Response.status(401).entity(exception.getMessage()).type("text/plain").build();
		}
		return Response.serverError().entity(exception.getMessage()).type("text/plain").build();
	}

}

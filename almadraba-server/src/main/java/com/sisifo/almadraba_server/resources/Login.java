package com.sisifo.almadraba_server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;

@Path("login")
public class Login {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getListOfApps(@Context SecurityContext securityContext) {
    	if (securityContext.getUserPrincipal() == null) {
    		throw new AlmadrabaAuthenticationException("body method");
    	}

        return "";
    }
}

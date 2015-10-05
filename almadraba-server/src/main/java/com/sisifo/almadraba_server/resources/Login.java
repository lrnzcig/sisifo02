package com.sisifo.almadraba_server.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.Session;

import com.sisifo.almadraba_server.AlmadrabaContextListener;
import com.sisifo.almadraba_server.data.AlmadrabaParamsArray;
import com.sisifo.almadraba_server.data.DatabaseUtils;
import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;

@Path("login")
public class Login {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AlmadrabaParamsArray loginAndGetCombos(@Context final SecurityContext securityContext) {
    	if (securityContext.getUserPrincipal() == null) {
    		throw new AlmadrabaAuthenticationException("body method");
    	}

    	Session session = AlmadrabaContextListener.getSessionFactory().getCurrentSession();
		session.beginTransaction();

    	DatabaseUtils.loadUserPageRankExec(session);

		session.disconnect();
		
		AlmadrabaParamsArray params = new AlmadrabaParamsArray();
		params.setExecutionLabels(DatabaseUtils.getExecutionLabels());

    	return params;
    }
}

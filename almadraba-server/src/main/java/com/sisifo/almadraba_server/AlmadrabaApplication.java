package com.sisifo.almadraba_server;

import javax.ws.rs.Priorities;

import org.glassfish.jersey.server.ResourceConfig;



public class AlmadrabaApplication extends ResourceConfig {

	public AlmadrabaApplication() {
		super();
		
		register(AlmadrabaAuthenticationRequestFilter.class, Priorities.AUTHENTICATION);
		register(AlmadrabaExceptionMapper.class);
		register(AlmadrabaContainerResponseFilter.class);
		
		packages("com.sisifo.almadraba_server.resources");
	}


}

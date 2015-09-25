package com.sisifo.almadraba_server;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.internal.util.Base64;

import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;

@PreMatching
public class AlmadrabaAuthenticationRequestFilter implements ContainerRequestFilter {
	
    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        String authentication = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authentication == null) {
        	// preflight request!
        	return;
        }
        if (! authentication.startsWith("Basic ")) {
            throw new AlmadrabaAuthenticationException("Not authenticated");
        }
        authentication = authentication.substring("Basic ".length());
        String[] values = Base64.decodeAsString(authentication).split(":");
        if (values.length < 2) {
            throw new AlmadrabaAuthenticationException("Invalid syntax for username and password");
        }
        
        final String username = values[0];
        String password = values[1];
        if (username == null || password == null || "".equals(username) || "".equals(password)) {
            throw new AlmadrabaAuthenticationException("Missing username or password");
        }
        
        requestContext.setSecurityContext(new SecurityContext() {
			
			@Override
			public boolean isUserInRole(String arg0) {
				return false;
			}
			
			@Override
			public boolean isSecure() {
				return true;
			}
			
			@Override
			public Principal getUserPrincipal() {
				return new Principal() {
					
					@Override
					public String getName() {
						return username;
					}
				};
			}
			
			@Override
			public String getAuthenticationScheme() {
				return "Basic";
			}
		});

    }

}

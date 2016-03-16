package com.sisifo.almadraba_server.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.Session;

import com.sisifo.almadraba_server.AlmadrabaContextListener;
import com.sisifo.almadraba_server.data.AlmadrabaUserFactory;
import com.sisifo.almadraba_server.data.DatabaseUtils;
import com.sisifo.almadraba_server.data.IAlmadrabaUser;
import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;
import com.sisifo.almadraba_server.hbm.Tweet;

import xre.AlmadrabaChart.UserType;
import xre.AlmadrabaUser;

@Path("user")
public class UserServices {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AlmadrabaUser getTweetUser(@Context final SecurityContext securityContext,
			final AlmadrabaUser input) {
    	if (securityContext.getUserPrincipal() == null) {
    		throw new AlmadrabaAuthenticationException("body method");
    	}
    	
    	Session session = AlmadrabaContextListener.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		
		IAlmadrabaUser user = AlmadrabaUserFactory.getUser(session, input.getUserPublicName(), input.getUserType());
		AlmadrabaUser output = user.getXRE();

		if (UserType.TWITTER.equals(input.getUserType())) {
			Tweet famousTweet = DatabaseUtils.getTweetUserFamousTweet(session, input.getUserId());
			if (famousTweet != null) {
				output.addAttribute(AlmadrabaUser.TWITTER_NOTORIOUS_TWEET_ATTR, famousTweet.getText());
			}
		}
		return output;
	}
}

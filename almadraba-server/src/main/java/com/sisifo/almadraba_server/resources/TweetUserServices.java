package com.sisifo.almadraba_server.resources;

import java.math.BigInteger;

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
import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;
import com.sisifo.almadraba_server.hbm.Tweet;
import com.sisifo.almadraba_server.hbm.TweetUser;

import xre.AlmadrabaChart.UserType;
import xre.AlmadrabaTweetUser;

@Path("user")
public class TweetUserServices {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AlmadrabaTweetUser getTweetUser(@Context final SecurityContext securityContext,
			final BigInteger userId) {
    	if (securityContext.getUserPrincipal() == null) {
    		throw new AlmadrabaAuthenticationException("body method");
    	}
    	
    	Session session = AlmadrabaContextListener.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		TweetUser tuser = (TweetUser) AlmadrabaUserFactory.getUser(session, userId, UserType.TWITTER);
		
		AlmadrabaTweetUser output = new AlmadrabaTweetUser();
		output.setUserId(userId);
		output.setFollowersCount(tuser.getFollowersCount());
		output.setFriendsCount(tuser.getFriendsCount());
		output.setStatusesCount(tuser.getStatusesCount());
		
		Tweet famousTweet = DatabaseUtils.getTweetUserFamousTweet(session, userId);
		if (famousTweet != null) {
			output.setNotoriousTweetText(famousTweet.getText());
		}
		
		return output;
	}
}

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
import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;
import com.sisifo.almadraba_server.hbm.GenericUser;
import com.sisifo.almadraba_server.hbm.Tweet;
import com.sisifo.almadraba_server.hbm.TweetUser;

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

		AlmadrabaUser output = new AlmadrabaUser();
		output.setUserType(input.getUserType());
		
		if (UserType.TWITTER.equals(input.getUserType())) {
			TweetUser tuser = (TweetUser) AlmadrabaUserFactory.getUser(session, input.getUserId(), input.getUserType());

			output.setUserId(input.getUserId());

			output.addAttribute(AlmadrabaUser.TWITTER_FOLLOWERS_COUNT_ATTR, tuser.getFollowersCount());			
			output.addAttribute(AlmadrabaUser.TWITTER_FRIENDS_COUNT_ATTR, tuser.getFriendsCount());	
			output.addAttribute(AlmadrabaUser.TWITTER_STATUSES_COUNT_ATTR, tuser.getStatusesCount());
			Tweet famousTweet = DatabaseUtils.getTweetUserFamousTweet(session, input.getUserId());
			if (famousTweet != null) {
				output.addAttribute(AlmadrabaUser.TWITTER_NOTORIOUS_TWEET_ATTR, famousTweet.getText());
			}
		} else {
			GenericUser guser = (GenericUser) AlmadrabaUserFactory.getUser(session, input.getUserPublicName(), input.getUserType());

			output.setUserPublicName(input.getUserPublicName());
			
			output.setUserId(guser.getId());
			output.setAttributes(guser.getOtherDataAttributes());
			output.addAttribute(AlmadrabaUser.GENERIC_GLOBAL_RANK_ATTR, String.valueOf(guser.getGlobalRank()));
		}
		
		return output;
	}
}

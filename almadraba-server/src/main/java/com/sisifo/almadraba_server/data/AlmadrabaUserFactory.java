package com.sisifo.almadraba_server.data;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;

import com.sisifo.almadraba_server.hbm.GenericUser;
import com.sisifo.almadraba_server.hbm.TweetUser;

import xre.AlmadrabaChart.UserType;

public class AlmadrabaUserFactory {

	public static IAlmadrabaUser getUser(final Session session, final BigInteger id, final UserType userType) {
		if (UserType.TWITTER.equals(userType)) {
			return getTweetUser(session, id);
		}
		return getUser(session, id);
	}
	
	public static IAlmadrabaUser getUser(final Session session, final String userName, final UserType userType) {
		if (UserType.TWITTER.equals(userType)) {
			return getTweetUser(session, new BigInteger(userName));
		}
		return getUser(session, userName);		
	}
	
	
	private static GenericUser getUser(final Session session, final BigInteger userId) {
		Criteria tuserC = session.createCriteria(GenericUser.class)
				.add(Property.forName("id").eq(userId));
		
		@SuppressWarnings("unchecked")
		List<GenericUser> users = tuserC.list();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	private static GenericUser getUser(final Session session, final String userName) {
		Criteria tuserC = session.createCriteria(GenericUser.class)
				.add(Property.forName("userName").eq(userName));
		
		@SuppressWarnings("unchecked")
		List<GenericUser> users = tuserC.list();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}


	private static TweetUser getTweetUser(final Session session, final BigInteger userId) {
		Criteria tuserC = session.createCriteria(TweetUser.class)
				.add(Property.forName("id").eq(userId));
		
		@SuppressWarnings("unchecked")
		List<TweetUser> users = tuserC.list();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

}

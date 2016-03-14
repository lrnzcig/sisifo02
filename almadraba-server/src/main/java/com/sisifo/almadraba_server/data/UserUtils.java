package com.sisifo.almadraba_server.data;

import java.math.BigInteger;

import org.hibernate.Session;

import com.sisifo.almadraba_server.hbm.UserRankExec;

import xre.AlmadrabaChart.UserType;

public class UserUtils {
	
	public static String getUserPublicName(final Session session, final BigInteger id, final UserType userType) {
		if (id == null) {
			return null;
		}
		return AlmadrabaUserFactory.getUser(session, id, userType).getUserName();
	}

	public static BigInteger getUserId(final Session session, final String userName, final UserType userType) {
		if (userName == null) {
			return null;
		}
		return AlmadrabaUserFactory.getUser(session, userName, userType).getId();
	}

	public static BigInteger[] getUserIdArray(final Session session, final String[] pinnedUsers, final UserType userType) {
		if (pinnedUsers == null) {
			return null;
		}
		BigInteger[] output = new BigInteger[pinnedUsers.length];
		for (int i = 0; i <= pinnedUsers.length - 1; i++ ) {
			output[i] = getUserId(session, pinnedUsers[i], userType);
		}
		return output;
	}

	public static UserType getUserType(final UserRankExec exec) {
		return UserType.forValue(exec.getUserType());
	}
	


	
}

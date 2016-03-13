package com.sisifo.almadraba_server.data;

import java.math.BigInteger;

public class UserUtils {

	public static String getUserPublicName(final BigInteger id) {
		if (id == null) {
			return null;
		}
		return (new AlmadrabaUserDefaultImplementation(id)).getPublicUserName();
	}

	public static BigInteger getUserId(final String userPublicName) {
		if (userPublicName == null) {
			return null;
		}
		return (new AlmadrabaUserDefaultImplementation(userPublicName)).getUserId();
	}

	public static BigInteger[] getUserIdArray(final String[] pinnedUsers) {
		if (pinnedUsers == null) {
			return null;
		}
		BigInteger[] output = new BigInteger[pinnedUsers.length];
		for (int i = 0; i <= pinnedUsers.length - 1; i++ ) {
			output[i] = getUserId(pinnedUsers[i]);
		}
		return output;
	}
	
}

package com.sisifo.almadraba_server.data;

import java.math.BigInteger;

public class AlmadrabaUserDefaultImplementation implements IAlmadrabaUser {
	
	private final BigInteger userId;

	public AlmadrabaUserDefaultImplementation(final BigInteger id) {
		this.userId = id;
	}

	public AlmadrabaUserDefaultImplementation(final String publicUserName) {
		this.userId = new BigInteger(publicUserName);
	}

	@Override
	public BigInteger getUserId() {
		return userId;
	}

	@Override
	public String getPublicUserName() {
		return String.valueOf(userId);
	}

}

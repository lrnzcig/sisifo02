package com.sisifo.almadraba_server.data;

import java.math.BigInteger;

/**
 * Different nodes of the graph of users implement this interface, e.g.
 * - twitter users
 * - generic users of wikipedia elections
 * 
 * @author lorenzorubio
 *
 */
public interface IAlmadrabaUser {
	
	BigInteger getUserId();
	
	String getPublicUserName();

}

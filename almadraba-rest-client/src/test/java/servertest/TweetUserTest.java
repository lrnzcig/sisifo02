package servertest;

import java.math.BigInteger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Assert;
import org.junit.Test;

import almadraba_rest_client.utils.ClientUtils;
import xre.AlmadrabaTweetUser;

public class TweetUserTest {

	@Test
	public void getTweetUser() {
		Client client = ClientUtils.getClientWithAuthenticationAndJackson();

		Response responseLogin = client.target("http://localhost:8080/almadraba/webapi").path("login").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.get();
		Assert.assertEquals(200, responseLogin.getStatus());

		BigInteger userId = BigInteger.valueOf(282339186);
		Response response = client.target("http://localhost:8080/almadraba/webapi").path("user").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.post(Entity.entity(userId, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, response.getStatus());
		AlmadrabaTweetUser user = response.readEntity(AlmadrabaTweetUser.class);
		Assert.assertEquals(userId, user.getUserId());
		//Assert.assertEquals(expected, user.getFollowersCount());
	}


}

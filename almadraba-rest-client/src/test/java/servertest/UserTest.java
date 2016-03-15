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
import xre.AlmadrabaChart.UserType;
import xre.AlmadrabaUser;

public class UserTest {

	@Test
	public void getTweetUser() {
		Client client = ClientUtils.getClientWithAuthenticationAndJackson();

		Response responseLogin = client.target("http://localhost:8080/almadraba/webapi").path("login").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.get();
		Assert.assertEquals(200, responseLogin.getStatus());

		BigInteger userId = BigInteger.valueOf(282339186);
		AlmadrabaUser input = new AlmadrabaUser();
		input.setUserId(userId);
		input.setUserType(UserType.TWITTER);
		Response response = client.target("http://localhost:8080/almadraba/webapi").path("user").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.post(Entity.entity(input, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, response.getStatus());
		AlmadrabaUser user = response.readEntity(AlmadrabaUser.class);
		Assert.assertEquals(userId, user.getUserId());
		Assert.assertEquals("182939", user.getAttribute(AlmadrabaUser.TWITTER_FOLLOWERS_COUNT_ATTR));
		Assert.assertEquals("59251", user.getAttribute(AlmadrabaUser.TWITTER_FRIENDS_COUNT_ATTR));
		Assert.assertEquals("207925", user.getAttribute(AlmadrabaUser.TWITTER_STATUSES_COUNT_ATTR));
		Assert.assertEquals("#DesmontandoACiudadanos :Lo que dice Arcadi Espada uno de los fundadores de #ciudadanos http://t.co/psQPH3qJ78  https://t.co/PK5STnCJpD",
				user.getAttribute(AlmadrabaUser.TWITTER_NOTORIOUS_TWEET_ATTR));
	}

	@Test
	public void getGenericUser() {
		Client client = ClientUtils.getClientWithAuthenticationAndJackson();

		Response responseLogin = client.target("http://localhost:8080/almadraba/webapi").path("login").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.get();
		Assert.assertEquals(200, responseLogin.getStatus());

		AlmadrabaUser input = new AlmadrabaUser();
		input.setUserPublicName("can't_sleep,_clown_will_eat_me");
		input.setUserType(UserType.GENERIC);
		Response response = client.target("http://localhost:8080/almadraba/webapi").path("user").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.post(Entity.entity(input, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, response.getStatus());
		AlmadrabaUser user = response.readEntity(AlmadrabaUser.class);
		Assert.assertEquals(BigInteger.valueOf(1049), user.getUserId());
		Assert.assertEquals("1", user.getAttribute(AlmadrabaUser.GENERIC_GLOBAL_RANK_ATTR));
		Assert.assertEquals("354", user.getAttribute("pos votes in"));
		Assert.assertEquals("47", user.getAttribute("neg votes in"));
		Assert.assertEquals("0", user.getAttribute("total votes out"));
	}

}

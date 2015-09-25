package servertest;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Assert;
import org.junit.Test;

import almadraba_rest_client.utils.ClientUtils;

public class LoginTest {

	@Test
	public void getApps() {
		Client client = ClientUtils.getClientWithAuthenticationAndJackson();

		Response response = client.target("http://localhost:8080/almadraba/webapi").path("login").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.get();
		Assert.assertEquals(200, response.getStatus());
		String kk = response.readEntity(String.class);
		System.out.println(kk);
	}
}

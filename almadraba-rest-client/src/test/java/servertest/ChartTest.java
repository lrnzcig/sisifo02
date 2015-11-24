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
import xre.AlmadrabaChart;
import xre.AlmadrabaChartParams;
import xre.AlmadrabaChartParams.QueryType;

public class ChartTest {

	@Test
	public void submitDataSimple() {
		Client client = ClientUtils.getClientWithAuthenticationAndJackson();

		AlmadrabaChartParams params = new AlmadrabaChartParams();
		params.setQueryType(QueryType.TOP);
		params.setNumber(5);
		params.setExecutionLabel("full");

		Response responseLogin = client.target("http://localhost:8080/almadraba/webapi").path("login").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.get();
		Assert.assertEquals(200, responseLogin.getStatus());

		Response response = client.target("http://localhost:8080/almadraba/webapi").path("chart").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.put(Entity.entity(params, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, response.getStatus());
		AlmadrabaChart chart = response.readEntity(AlmadrabaChart.class);
		System.out.println(chart.getSeries().length);
		System.out.println(chart.getStepIds().length);		
	}

	@Test
	public void submitDataQueryTypeNext() {
		Client client = ClientUtils.getClientWithAuthenticationAndJackson();

		AlmadrabaChartParams params = new AlmadrabaChartParams();
		params.setQueryType(QueryType.TOP);
		params.setNumber(5);
		params.setExecutionLabel("full");

		Response responseLogin = client.target("http://localhost:8080/almadraba/webapi").path("login").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.get();
		Assert.assertEquals(200, responseLogin.getStatus());

		Response response = client.target("http://localhost:8080/almadraba/webapi").path("chart").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.put(Entity.entity(params, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, response.getStatus());
		AlmadrabaChart chart = response.readEntity(AlmadrabaChart.class);
		System.out.println(chart.getSeries().length);
		Assert.assertEquals(BigInteger.valueOf(187564239), chart.getSeries()[0].getUserId());
		Assert.assertEquals(BigInteger.valueOf(20909329), chart.getSeries()[4].getUserId());
		System.out.println(chart.getStepIds().length);		

		params.setQueryType(QueryType.NEXT);
		params.setNumber(5);
		params.setExecutionLabel("full");
		BigInteger[] nonPinnedUsers = new BigInteger[chart.getSeries().length];
		for (int i = 0; i < chart.getSeries().length; i++) {
			nonPinnedUsers[i] = chart.getSeries()[i].getUserId();
		}
		params.setNonPinnedUsers(nonPinnedUsers);

		response = client.target("http://localhost:8080/almadraba/webapi").path("chart").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.put(Entity.entity(params, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, response.getStatus());
		chart = response.readEntity(AlmadrabaChart.class);
		Assert.assertNotEquals(BigInteger.valueOf(187564239), chart.getSeries()[0].getUserId());
		Assert.assertNotEquals(BigInteger.valueOf(20909329), chart.getSeries()[4].getUserId());
		System.out.println(chart.getSeries().length);
		System.out.println(chart.getStepIds().length);		


	}

}

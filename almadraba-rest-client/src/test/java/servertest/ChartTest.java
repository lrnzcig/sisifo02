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
		Assert.assertEquals(5, chart.getSeries().length);
		Assert.assertEquals(17, chart.getStepIds().length);	 // TODO should be 17 ==> careful when loading data from python
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
		Assert.assertEquals(5, chart.getSeries().length);
		Assert.assertEquals(17, chart.getStepIds().length);	 // TODO should be 17 ==> careful when loading data from python
		Assert.assertEquals(String.valueOf(282339186), chart.getSeries(BigInteger.valueOf(1)).getUserId());
		Assert.assertEquals(String.valueOf(20909329), chart.getSeries(BigInteger.valueOf(2)).getUserId());
		Assert.assertEquals(String.valueOf(341657886), chart.getSeries(BigInteger.valueOf(3)).getUserId());
		Assert.assertEquals(String.valueOf(173665005), chart.getSeries(BigInteger.valueOf(4)).getUserId());
		Assert.assertEquals(String.valueOf(187564239), chart.getSeries(BigInteger.valueOf(5)).getUserId());

		params.setQueryType(QueryType.NEXT);
		params.setNumber(5);
		params.setExecutionLabel("full");
		String[] nonPinnedUsers = new String[chart.getSeries().length];
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
		Assert.assertEquals(5, chart.getSeries().length);
		Assert.assertEquals(17, chart.getStepIds().length);	 // TODO should be 17 ==> careful when loading data from python
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(1)));
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(2)));
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(3)));
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(4)));
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(5)));
	}

	@Test
	public void submitDataQueryTypePinnedUser() {
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
		Assert.assertEquals(5, chart.getSeries().length);
		Assert.assertEquals(17, chart.getStepIds().length);	 // TODO should be 17 ==> careful when loading data from python
		Assert.assertEquals(String.valueOf(282339186), chart.getSeries(BigInteger.valueOf(1)).getUserId());
		Assert.assertEquals(String.valueOf(20909329), chart.getSeries(BigInteger.valueOf(2)).getUserId());
		Assert.assertEquals(String.valueOf(341657886), chart.getSeries(BigInteger.valueOf(3)).getUserId());
		Assert.assertEquals(String.valueOf(173665005), chart.getSeries(BigInteger.valueOf(4)).getUserId());
		Assert.assertEquals(String.valueOf(187564239), chart.getSeries(BigInteger.valueOf(5)).getUserId());

		params.setQueryType(QueryType.NEXT);
		params.setNumber(5);
		params.setExecutionLabel("full");
		params.setPinnedUsers(new String[] {String.valueOf(282339186)});
		String[] nonPinnedUsers = new String[chart.getSeries().length];
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
		Assert.assertEquals(6, chart.getSeries().length);
		Assert.assertEquals(17, chart.getStepIds().length);	 // TODO should be 17 ==> careful when loading data from python
		Assert.assertEquals(String.valueOf(282339186), chart.getSeries(BigInteger.valueOf(1)).getUserId());
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(2)));
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(3)));
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(4)));
		Assert.assertNull(chart.getSeries(BigInteger.valueOf(5)));
		Assert.assertEquals(String.valueOf(3131660806L), chart.getSeries(BigInteger.valueOf(6)).getUserId());
		Assert.assertEquals(String.valueOf(267058747), chart.getSeries(BigInteger.valueOf(7)).getUserId());
		Assert.assertEquals(String.valueOf(3135332230L), chart.getSeries(BigInteger.valueOf(8)).getUserId());
		Assert.assertEquals(String.valueOf(1652459718), chart.getSeries(BigInteger.valueOf(9)).getUserId());
		Assert.assertEquals(String.valueOf(2531053765L), chart.getSeries(BigInteger.valueOf(10)).getUserId());
	}

}

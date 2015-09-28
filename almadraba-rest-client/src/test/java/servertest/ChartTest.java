package servertest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Assert;
import org.junit.Test;

import almadraba_rest_client.utils.ClientUtils;
import xre.AlmadrabaChart;
import xre.AlmadrabaChartParameters;
import xre.AlmadrabaChartParameters.QueryType;

public class ChartTest {

	@Test
	public void submitData() {
		Client client = ClientUtils.getClientWithAuthenticationAndJackson();

		AlmadrabaChartParameters params = new AlmadrabaChartParameters();
		params.setQueryType(QueryType.TOP);
		params.setNumber(5);

		Response response = client.target("http://localhost:8080/almadraba/webapi").path("chart").request()
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "kk")
				.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "pass")
				.put(Entity.entity(params, MediaType.APPLICATION_JSON));
		Assert.assertEquals(200, response.getStatus());
		AlmadrabaChart chart = response.readEntity(AlmadrabaChart.class);
		System.out.println(chart.getSeries().length);
		System.out.println(chart.getStepIds().length);		
	}
}

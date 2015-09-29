package com.sisifo.almadraba_server.resources;

import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.Session;

import com.sisifo.almadraba_server.AlmadrabaContextListener;
import com.sisifo.almadraba_server.data.DatabaseUtils;
import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;
import com.sisifo.almadraba_server.hbm.UserPageRankEvolution;

import xre.AlmadrabaChart;
import xre.AlmadrabaChartParameters;
import xre.AlmadrabaChartParameters.QueryType;

@Path("chart")
public class Chart {

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public AlmadrabaChart getUserName(@Context final SecurityContext securityContext,
    		final AlmadrabaChartParameters params) {
    	if (securityContext.getUserPrincipal() == null) {
    		throw new AlmadrabaAuthenticationException("body method");
    	}
    	
    	Session session = AlmadrabaContextListener.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		int number = params.getNumber();
		QueryType type = params.getQueryType();
		
		List<UserPageRankEvolution> rowsSql = DatabaseUtils.getTopUserSeriesSQL(session, number, "full", null); // "window"
		System.out.println(rowsSql.size());
		
		session.disconnect();
		
		AlmadrabaChart chart = new AlmadrabaChart();
		DatabaseUtils.addDatabaseRowsToChartSeries(chart, rowsSql);

        return chart;
    }
}

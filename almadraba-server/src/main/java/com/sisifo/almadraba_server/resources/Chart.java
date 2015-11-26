package com.sisifo.almadraba_server.resources;

import java.math.BigInteger;
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
import com.sisifo.almadraba_server.hbm.UserPageRankExec;

import xre.AlmadrabaChart;
import xre.AlmadrabaChartParams;
import xre.AlmadrabaChartParams.QueryType;

@Path("chart")
public class Chart {

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public AlmadrabaChart getUserName(@Context final SecurityContext securityContext,
    		final AlmadrabaChartParams params) {
    	if (securityContext.getUserPrincipal() == null) {
    		throw new AlmadrabaAuthenticationException("body method");
    	}
    	
    	Session session = AlmadrabaContextListener.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		int number = params.getNumber();
		QueryType type = params.getQueryType();

		UserPageRankExec exec = DatabaseUtils.getExecution(params.getExecutionLabel());
		Integer rankExecId;
		if (exec != null) {
			rankExecId = exec.getId();
		} else {
			// TODO message to user
			rankExecId = DatabaseUtils.getExecution(DatabaseUtils.getExecutionLabels()[0]).getId();
		}
		
		List<UserPageRankEvolution> rowsSql;
		
		if (QueryType.TOP.equals(type)) {
			rowsSql = DatabaseUtils.getMaxUserSeriesSQL(session, number, rankExecId, params.getPinnedUsers());
		} else {
			int lastIdRowNumber = getMinUserId(session, params.getNonPinnedUsers(), rankExecId);
			rowsSql = DatabaseUtils.getMaxUserSeriesSQL(session, number, lastIdRowNumber, rankExecId, params.getPinnedUsers());
		}
		
		session.disconnect();
		
		AlmadrabaChart chart = new AlmadrabaChart();
		DatabaseUtils.addDatabaseRowsToChartSeries(session, chart, rankExecId, rowsSql);

        return chart;
    }

	private int getMinUserId(final Session session, final BigInteger[] nonPinnedUsers, final Integer rankExecId) {
		int result = 0;
		for (BigInteger id : nonPinnedUsers) {
			int candidate = DatabaseUtils.getRowNumberForUserSQL(session, id, rankExecId);
			if (candidate > result) {
				result = candidate;
			}
		}
		return result;
	}
}

package com.sisifo.almadraba_server.resources;

import java.util.Arrays;
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
import com.sisifo.almadraba_server.data.UserUtils;
import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;
import com.sisifo.almadraba_server.hbm.UserRankEvolution;
import com.sisifo.almadraba_server.hbm.UserRankExec;

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

		UserRankExec exec = DatabaseUtils.getExecution(params.getExecutionLabel());
		Integer rankExecId;
		if (exec != null) {
			rankExecId = exec.getId();
		} else {
			// TODO message to user
			rankExecId = DatabaseUtils.getExecution(DatabaseUtils.getExecutionLabels()[0]).getId();
		}
		
		List<UserRankEvolution> rowsSql;
		
		if (QueryType.TOP.equals(type)) {
			rowsSql = DatabaseUtils.getMaxUserSeriesSQL(session, number, rankExecId, UserUtils.getUserIdArray(params.getPinnedUsers()));
		} else if (QueryType.NEXT.equals(type)) {
			Integer lastIdRowNumber = null;
			if (params.getNonPinnedUsers() != null && params.getNonPinnedUsers().length > 0) {
				lastIdRowNumber = getMinUserId(session, params.getNonPinnedUsers(), rankExecId);
			}
			rowsSql = DatabaseUtils.getMaxUserSeriesSQL(session, number, lastIdRowNumber, rankExecId, UserUtils.getUserIdArray(params.getPinnedUsers()));
		} else {
			// same results as before
			String[] usersToShow = Arrays.copyOf(params.getNonPinnedUsers(), params.getNonPinnedUsers().length + params.getPinnedUsers().length);
			System.arraycopy(params.getPinnedUsers(), 0, usersToShow, params.getNonPinnedUsers().length, params.getPinnedUsers().length);
			rowsSql = DatabaseUtils.getMaxUserSeriesSQL(session, 0, rankExecId, UserUtils.getUserIdArray(usersToShow));
		}
		
		session.disconnect();
		
		AlmadrabaChart chart = new AlmadrabaChart();
		DatabaseUtils.addDatabaseRowsToChartSeries(session, chart, rankExecId, rowsSql);

        return chart;
    }

	private int getMinUserId(final Session session, final String[] nonPinnedUsers, final Integer rankExecId) {
		int result = 0;
		// get just the rows for the nonPinnedUsers
		List<UserRankEvolution> rowsSql = DatabaseUtils.getMaxUserSeriesSQL(session, 0, rankExecId, UserUtils.getUserIdArray(nonPinnedUsers));
		for (UserRankEvolution row : rowsSql) {
			int candidate = row.getRowNumber().intValue();
			if (candidate > result) {
				result = candidate;
			}
		}
		return result;
	}
}

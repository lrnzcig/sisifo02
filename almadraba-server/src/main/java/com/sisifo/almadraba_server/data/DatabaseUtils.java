package com.sisifo.almadraba_server.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

import com.sisifo.almadraba_server.exception.AlmadrabaCodingErrorException;
import com.sisifo.almadraba_server.hbm.UserPageRankEvolution;
import com.sisifo.almadraba_server.hbm.UserPageRankEvolutionId;
import com.sisifo.almadraba_server.hbm.UserPageRankExec;
import com.sisifo.almadraba_server.hbm.UserPageRankExecStep;

import xre.AlmadrabaChart;
import xre.AlmadrabaSeries;

public class DatabaseUtils {

	@SuppressWarnings("unchecked")
	@Deprecated
	public static List<UserPageRankEvolution> getTopUserSeries(final Session session, final int number, final BigInteger[] additionalUserIds) {
		// 1st select the users as a subquery	
		ProjectionList pl = Projections.projectionList()
				.add(Projections.property("id.userId"))
				.add(Projections.sqlProjection("row_number() over() as rownum", new String[] {"rownum"}, new Type[] { new IntegerType() }));
		Criteria usersC = session.createCriteria(UserPageRankEvolution.class)
				.addOrder(Property.forName("rank").desc())
				.add(Property.forName("id.rankExecId").eq(new String("full")))
				.add(Property.forName("id.rankStepId").eq(new String("rank")))
				.setProjection(pl);
		
		// top5 in Java
		List<Object[]> usersL = usersC.list();
		BigInteger[] users = new BigInteger[number];
		for (int i = 0; i < number; i++) {
			users[i] = (BigInteger) usersL.get(i)[0];
		}
		
		if (additionalUserIds != null) {
			users = Arrays.copyOf(users, number + additionalUserIds.length);
			int i = number;
			for (BigInteger userId : additionalUserIds) {
				users[i++] = userId;
			}
		}
		
		// get series
		Criteria criteria = session.createCriteria(UserPageRankEvolution.class)
				.add(Restrictions.in("id.userId", users))
				.add(Property.forName("id.rankExecId").eq(new String("full")));
		return criteria.list();
	}

	/**
	 * Execute query directly in SQL
	 * Max means that the user order is solved using the maximum step_order in the page rank evolution
	 * TODO
	 * TODO does this make sense???
	 * TODO
	 * 
	 * @param session
	 * @param number
	 * @param additionalIds e.g. new BigInteger[] {BigInteger.valueOf(38643994)}
	 * @return
	 */
	public static List<UserPageRankEvolution> getMaxUserSeriesSQL(final Session session, final int number, final Integer lastIdRowNumber,
			final Integer rankExecId, final BigInteger[] additionalIds) {
		// 1. composing query
		String queryText = "select * from user_page_rank_evolution"
				+ " where (user_id in (select user_id"
				+ "	    	from ("
				+ getPageRankEvolutionMaxOrder()
				+ "	    	) as maxrows"
				+ "	    	where rownumber <= :max_number";
		if (lastIdRowNumber != null) {
			queryText += " and rownumber >= :min_number";
		}
		queryText += ")";
		if (additionalIds != null) {
			queryText = queryText + "     or user_id in (:list))";
		} else {
			queryText = queryText + ")";
		}
		queryText = queryText 
				+ "	and rank_exec_id = :rank_exec_id"
				+ " order by user_id, step_order";
		
		// 2. calculating min and max orders
		int max_number = number;
		int min_number = 0;
		if (lastIdRowNumber != null) {
			min_number = lastIdRowNumber + 1;
			max_number = min_number + number - 1;
		}
		
		// 3. setting query variables
		Query q = session.createSQLQuery(queryText)
				.setInteger("rank_exec_id", rankExecId)
				.setInteger("max_number", max_number);
		if (lastIdRowNumber != null) {
			q.setInteger("min_number", min_number);
		}
		if (additionalIds != null) {
			q.setParameterList("list", additionalIds);
		}
		
		// 4. launching query and output
		List<UserPageRankEvolution> output = new ArrayList<UserPageRankEvolution>();
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.list(); 
		for (Object[] result : results) {
			UserPageRankEvolution row = new UserPageRankEvolution();
			output.add(row);
			row.setId(new UserPageRankEvolutionId((BigInteger) result[0], (Integer) result[1], (Integer) result[2]));
			double rank = (double) result[3];
			row.setRank((float) rank);
		}
		
		return output;
	}
	
	/**
	 * Max means that the user order is solved using the maximum step_order in the page rank evolution
	 * TODO
	 * TODO does this make sense???
	 * TODO
	 * 
	 * @return
	 */
	private static String getPageRankEvolutionMaxOrder() {
		return "select user_id as user_id, row_number() over(order by rank desc) as rownumber"
				+ "	    	from user_page_rank_evolution"
				+ "	    	where (rank_exec_id, step_order) = "
				+ "             (select :rank_exec_id, max(step_order) from user_page_rank_evolution where rank_exec_id = :rank_exec_id)";
	}

	/**
	 * Max means that the user order is solved using the maximum step_order in the page rank evolution
	 * TODO
	 * TODO does this make sense???
	 * TODO
	 * 
	 * @param session
	 * @param number
	 * @param rankExecId
	 * @param pinnedUsers
	 * @return
	 */
	public static List<UserPageRankEvolution> getMaxUserSeriesSQL(final Session session, final int number, 
			final Integer rankExecId, final BigInteger[] pinnedUsers) {
		return getMaxUserSeriesSQL(session, number, null, rankExecId, pinnedUsers);
	}


	public static int getRowNumberForUserSQL(final Session session, final BigInteger lastUserId, final int rankExecId) {
		String queryText = "select * from (" +getPageRankEvolutionMaxOrder() + ") as maxrows where user_id = :user_id";
		Query q = session.createSQLQuery(queryText)
				.setInteger("rank_exec_id", rankExecId)
				.setBigInteger("user_id", lastUserId);

		@SuppressWarnings("unchecked")
		List<Object[]> results = q.list(); 
		for (Object[] result : results) {
			return ((BigInteger)result[1]).intValue();
		}

		throw new AlmadrabaCodingErrorException("Can't find user_id=" + lastUserId);
	}


	/**
	 * Returns a query. To paginate over it do something like
	 * 		@SuppressWarnings("unchecked")
	 *		List<UserPageRankEvolution> rowsPaginate = rowsPaginateQuery.setFirstResult(0).setMaxResults(number*7).list();
	 *
	 * @param session
	 * @param number
	 * @return
	 */
	@Deprecated
	public static Criteria getTopUserSeriesPaginate(final Session session, final int number) {
		ProjectionList pl = Projections.projectionList()
				.add(Projections.property("id.userId"));

		// this does not select any user, it just orders them
		DetachedCriteria subCriteria = DetachedCriteria.forClass(UserPageRankEvolution.class)
				.addOrder(Property.forName("rank").desc())
				.add(Property.forName("id.rankExecId").eq(new String("full")))
				.add(Property.forName("id.rankStepId").eq(new String("rank")))
				.setProjection(pl);

		Criteria criteria2 = session.createCriteria(UserPageRankEvolution.class)
				.add(Property.forName("id.userId").in(subCriteria))
				.add(Property.forName("id.rankExecId").eq(new String("full")));
		return criteria2;
	}

	
	public static void addDatabaseRowsToChartSeries(final Session session, final AlmadrabaChart chart, final Integer rankExecId,
			final List<UserPageRankEvolution> rows) {
		Map<BigInteger, AlmadrabaSeries> mapSeries = new HashMap<BigInteger, AlmadrabaSeries>();
		for (UserPageRankEvolution upre : rows) {
			BigInteger id = upre.getId().getUserId();
			if (mapSeries.get(id) == null) {
				mapSeries.put(id, new AlmadrabaSeries(id));				
			}
			
			AlmadrabaSeries series = mapSeries.get(id);
			series.addItemToSeries(upre.getRank());
		}
		
		for (BigInteger id : mapSeries.keySet()) {
			chart.addSeriesItem(mapSeries.get(id));
		}
		
		UserPageRankExec exec = getUserPageRankExec(session, rankExecId);
		chart.setRankExecLabel(exec.getRankExecLabel());
		chart.setHourStep(exec.getHourStep());
		
		List<UserPageRankExecStep> execSteps = getUserPageRankExecStep(session, rankExecId);
		for (UserPageRankExecStep execStep : execSteps) {
			chart.addStepIdItem(execStep.getRankStepLabel());
		}
	}

	@SuppressWarnings("unchecked")
	private static List<UserPageRankExecStep> getUserPageRankExecStep(final Session session, final int rankExecId) {
		Criteria criteria = session.createCriteria(UserPageRankExecStep.class)
				.add(Property.forName("rankExecId").eq(rankExecId));
		return criteria.list();
	}

	private static UserPageRankExec getUserPageRankExec(final Session session, final int rankExecId) {
		Criteria criteria = session.createCriteria(UserPageRankExec.class)
				.add(Property.forName("id").eq(rankExecId));
		return (UserPageRankExec) criteria.list().get(0);
	}


	private static Map<String, UserPageRankExec> executions = new HashMap<String, UserPageRankExec>();

	public static void loadUserPageRankExec(final Session session) {
		Criteria criteria = session.createCriteria(UserPageRankExec.class);
		@SuppressWarnings("unchecked")
		List<UserPageRankExec> upreList = criteria.list();
		for (UserPageRankExec upre : upreList) {
			executions.put(upre.getRankExecLabel(), upre);
		}
	}
	
	public static UserPageRankExec getExecution(final String value) {
		if (executions.keySet().isEmpty()) {
			throw new RuntimeException("UserPageRankExec have not been loaded at login");
		}
		return executions.get(value);
	}

	public static String[] getExecutionLabels() {
		Set<String> set = executions.keySet();
		String[] output = new String[set.size()];
		int i = 0;
		for (String exec : set) {
			output[i++] = exec;  
		}
		return output;
	}

}

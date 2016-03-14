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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

import com.sisifo.almadraba_server.hbm.Tweet;
import com.sisifo.almadraba_server.hbm.TweetUser;
import com.sisifo.almadraba_server.hbm.UserRankEvolution;
import com.sisifo.almadraba_server.hbm.UserRankEvolutionId;
import com.sisifo.almadraba_server.hbm.UserRankExec;
import com.sisifo.almadraba_server.hbm.UserRankExecStep;

import xre.AlmadrabaChart;
import xre.AlmadrabaSeries;

public class DatabaseUtils {

	@SuppressWarnings("unchecked")
	@Deprecated
	public static List<UserRankEvolution> getTopUserSeries(final Session session, final int number, final BigInteger[] additionalUserIds) {
		// 1st select the users as a subquery	
		ProjectionList pl = Projections.projectionList()
				.add(Projections.property("id.userId"))
				.add(Projections.sqlProjection("row_number() over() as rownum", new String[] {"rownum"}, new Type[] { new IntegerType() }));
		Criteria usersC = session.createCriteria(UserRankEvolution.class)
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
		Criteria criteria = session.createCriteria(UserRankEvolution.class)
				.add(Restrictions.in("id.userId", users))
				.add(Property.forName("id.rankExecId").eq(new String("full")));
		return criteria.list();
	}
	
	public static TweetUser getTweetUser(final Session session, final BigInteger userId) {
		Criteria tuserC = session.createCriteria(TweetUser.class)
				.add(Property.forName("id").eq(userId));
		
		@SuppressWarnings("unchecked")
		List<TweetUser> users = tuserC.list();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	public static Tweet getTweetUserFamousTweet(final Session session, final BigInteger userId) {
		Criteria tweetC = session.createCriteria(Tweet.class)
				.add(Property.forName("userId").eq(userId))
				.addOrder(Order.desc("retweetCount"));
		
		@SuppressWarnings("unchecked")
		List<Tweet> tweets = tweetC.list();
		if (tweets.isEmpty()) {
			return null;
		}
		return tweets.get(0);
	}

	/**
	 * Execute query directly in SQL
	 * Max means that the user order is solved using the maximum step_order in the rank evolution
	 * TODO
	 * TODO does this make sense???
	 * TODO
	 * 
	 * @param session
	 * @param number
	 * @param additionalIds e.g. new BigInteger[] {BigInteger.valueOf(38643994)}
	 * @return
	 */
	public static List<UserRankEvolution> getMaxUserSeriesSQL(final Session session, final int number, final Integer lastIdRowNumber,
			final Integer rankExecId, final BigInteger[] additionalIds) {
		// 1. composing query
		boolean minRowNumberPresent = lastIdRowNumber != null;
		boolean additionalIdsPresent = additionalIds != null && additionalIds.length > 0; 
		String queryText = getRankEvolutionOrderedForRangeQuery(minRowNumberPresent, additionalIdsPresent);
		
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
		if (minRowNumberPresent) {
			q.setInteger("min_number", min_number);
		}
		if (additionalIdsPresent) {
			q.setParameterList("list", additionalIds);
		}
		
		// 4. launching query and output
		List<UserRankEvolution> output = new ArrayList<UserRankEvolution>();
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.list(); 
		for (Object[] result : results) {
			UserRankEvolution row = new UserRankEvolution();
			output.add(row);
			row.setId(new UserRankEvolutionId((BigInteger) result[0], (Integer) result[1], (Integer) result[2]));
			double rank = (double) result[3];
			row.setRank((float) rank);
			row.setRowNumber((BigInteger) result[4]);
		}
		
		return output;
	}
	
	/**
	 * Max means that the user order is solved using the maximum step_order in the rank evolution
	 * TODO
	 * TODO does this make sense???
	 * TODO
	 * 
	 * @return
	 */
	private static String getRankEvolutionMaxOrderQuery() {
		return "select user_id as user_id, row_number() over(order by rank desc) as rownumber"
				+ "	    	from user_rank_evolution"
				+ "	    	where (rank_exec_id, step_order) = "
				+ "             (select :rank_exec_id, max(step_order) from user_rank_evolution where rank_exec_id = :rank_exec_id)";
	}
	
	private static String getRankEvolutionMaxOrderRangeQuery(final boolean minRowNumberPresent, final boolean additionalIdsPresent) {
		String queryText = "select user_id, rownumber from ("
							+ getRankEvolutionMaxOrderQuery()
							+ ") as maxrows "
							+ "where (rownumber <= :max_number";
		if (minRowNumberPresent) {
			queryText += " and rownumber >= :min_number";
		}
		queryText += ")";
		if (additionalIdsPresent) {
			queryText = queryText + "or user_id in (:list)";			
		}
		return queryText;
	}
	
	private static String getRankEvolutionOrderedForRangeQuery(final boolean minRowNumberPresent, final boolean additionalIdsPresent) {
		return "select upre.*, user_rownumber.rownumber "
				+ "from user_rank_evolution as upre, (" + getRankEvolutionMaxOrderRangeQuery(minRowNumberPresent, additionalIdsPresent) 
														+ ") as user_rownumber "
				+ "where user_rownumber.user_id = upre.user_id "
				+ "and rank_exec_id = :rank_exec_id "
				+ "order by user_rownumber.rownumber, step_order";
	}

	/**
	 * Max means that the user order is solved using the maximum step_order in the rank evolution
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
	public static List<UserRankEvolution> getMaxUserSeriesSQL(final Session session, final int number, 
			final Integer rankExecId, final BigInteger[] pinnedUsers) {
		return getMaxUserSeriesSQL(session, number, null, rankExecId, pinnedUsers);
	}



	/**
	 * Returns a query. To paginate over it do something like
	 * 		@SuppressWarnings("unchecked")
	 *		List<UserRankEvolution> rowsPaginate = rowsPaginateQuery.setFirstResult(0).setMaxResults(number*7).list();
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
		DetachedCriteria subCriteria = DetachedCriteria.forClass(UserRankEvolution.class)
				.addOrder(Property.forName("rank").desc())
				.add(Property.forName("id.rankExecId").eq(new String("full")))
				.add(Property.forName("id.rankStepId").eq(new String("rank")))
				.setProjection(pl);

		Criteria criteria2 = session.createCriteria(UserRankEvolution.class)
				.add(Property.forName("id.userId").in(subCriteria))
				.add(Property.forName("id.rankExecId").eq(new String("full")));
		return criteria2;
	}

	
	public static void addDatabaseRowsToChartSeries(final Session session, final AlmadrabaChart chart, final Integer rankExecId,
			final List<UserRankEvolution> rows) {
		Map<BigInteger, AlmadrabaSeries> mapSeries = new HashMap<BigInteger, AlmadrabaSeries>();
		for (UserRankEvolution upre : rows) {
			BigInteger id = upre.getId().getUserId();
			if (mapSeries.get(id) == null) {
				mapSeries.put(id, new AlmadrabaSeries(UserUtils.getUserPublicName(id)));
			}
			
			AlmadrabaSeries series = mapSeries.get(id);
			series.addItemToSeries(upre.getRank());
			series.setRowNumber(upre.getRowNumber());
		}
		
		for (BigInteger id : mapSeries.keySet()) {
			chart.addSeriesItem(mapSeries.get(id));
		}
		
		UserRankExec exec = getUserRankExec(session, rankExecId);
		chart.setRankExecLabel(exec.getRankExecLabel());
		chart.setHourStep(exec.getHourStep());
		
		List<UserRankExecStep> execSteps = getUserRankExecStep(session, rankExecId);
		for (UserRankExecStep execStep : execSteps) {
			chart.addStepIdItem(execStep.getRankStepLabel());
		}
	}

	@SuppressWarnings("unchecked")
	private static List<UserRankExecStep> getUserRankExecStep(final Session session, final int rankExecId) {
		Criteria criteria = session.createCriteria(UserRankExecStep.class)
				.add(Property.forName("rankExecId").eq(rankExecId));
		return criteria.list();
	}

	private static UserRankExec getUserRankExec(final Session session, final int rankExecId) {
		Criteria criteria = session.createCriteria(UserRankExec.class)
				.add(Property.forName("id").eq(rankExecId));
		return (UserRankExec) criteria.list().get(0);
	}


	private static Map<String, UserRankExec> executions = new HashMap<String, UserRankExec>();

	public static void loadUserRankExec(final Session session) {
		Criteria criteria = session.createCriteria(UserRankExec.class);
		@SuppressWarnings("unchecked")
		List<UserRankExec> upreList = criteria.list();
		for (UserRankExec upre : upreList) {
			executions.put(upre.getRankExecLabel(), upre);
		}
	}
	
	public static UserRankExec getExecution(final String value) {
		if (executions.keySet().isEmpty()) {
			throw new RuntimeException("UserRankExec have not been loaded at login");
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

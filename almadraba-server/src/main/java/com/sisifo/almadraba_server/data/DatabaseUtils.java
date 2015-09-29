package com.sisifo.almadraba_server.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sisifo.almadraba_server.hbm.UserPageRankEvolution;
import com.sisifo.almadraba_server.hbm.UserPageRankEvolutionId;

import xre.AlmadrabaChart;
import xre.AlmadrabaSeries;

public class DatabaseUtils {

	@SuppressWarnings("unchecked")
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
	 * 
	 * @param session
	 * @param number
	 * @param additionalIds e.g. new BigInteger[] {BigInteger.valueOf(38643994)}
	 * @return
	 */
	public static List<UserPageRankEvolution> getTopUserSeriesSQL(final Session session, final int number, 
			final String rankExecId, final BigInteger[] additionalIds) {
		// TODO this assumes we want users order by full page rank and assuming name of the column also
		String queryText = "select * from user_page_rank_evolution"
				+ " where (user_id in (select user_id"
				+ "	    	from (select user_id as user_id, row_number() over(order by rank desc) as rownumber"
				+ "	    	from user_page_rank_evolution"
				+ "	    	where rank_exec_id = 'full'"
				+ "	    	and rank_step_id = 'rank') as maxrows"
				+ "	    	where rownumber <= :number)";
		if (additionalIds != null) {
			queryText = queryText + "     or user_id in (:list))";
		} else {
			queryText = queryText + ")";
		}
		queryText = queryText 
				+ "	and rank_exec_id = :rank_exec_id"
				+ " order by user_id, rank_step_id";
		
		Query q = session.createSQLQuery(queryText)
				.setString("rank_exec_id", rankExecId)
				.setInteger("number", number);
		if (additionalIds != null) {
			q.setParameterList("list", additionalIds);
		}
		
		List<UserPageRankEvolution> output = new ArrayList<UserPageRankEvolution>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.list(); 
		for (Object[] result : results) {
			UserPageRankEvolution row = new UserPageRankEvolution();
			output.add(row);
			row.setId(new UserPageRankEvolutionId((BigInteger) result[0], (String) result[1], (String) result[2]));
			double rank = (double) result[3];
			row.setRank((float) rank);
		}
		
		return output;
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

	
	public static void addDatabaseRowsToChartSeries(final AlmadrabaChart chart, final List<UserPageRankEvolution> rows) {
		Map<BigInteger, AlmadrabaSeries> mapSeries = new HashMap<BigInteger, AlmadrabaSeries>();
		BigInteger firstId = null;
		for (UserPageRankEvolution upre : rows) {
			BigInteger id = upre.getId().getUserId();
			if (mapSeries.get(id) == null) {
				mapSeries.put(id, new AlmadrabaSeries(id));				
			}
			
			if (firstId == null || firstId.equals(id)) {
				firstId = id;
				// setup general information for the chart - all users have the same info
				if (chart.getRankExecId() == null) {
					chart.setRankExecId(upre.getId().getRankExecId());
				}
				if (chart.getStepIds() == null 
						|| ! Arrays.asList(chart.getStepIds()).contains(upre.getId().getRankStepId())) {
					chart.addStepIdItem(upre.getId().getRankStepId());
				}
			}
			
			AlmadrabaSeries series = mapSeries.get(id);
			series.addItemToSeries(upre.getRank());
		}
		
		for (BigInteger id : mapSeries.keySet()) {
			chart.addSeriesItem(mapSeries.get(id));
		}
	}

}

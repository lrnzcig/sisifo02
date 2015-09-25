package com.sisifo.almadraba_server.resources;

import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

import com.sisifo.almadraba_server.AlmadrabaContextListener;
import com.sisifo.almadraba_server.exception.AlmadrabaAuthenticationException;
import com.sisifo.almadraba_server.hbm.UserPageRankEvolution;

import xre.AlmadrabaChart;
import xre.AlmadrabaChartParameters;
import xre.AlmadrabaChartParameters.QueryType;

@Path("chart")
public class Chart {

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public AlmadrabaChart getUserName(@Context SecurityContext securityContext,
    		AlmadrabaChartParameters params) {
    	if (securityContext.getUserPrincipal() == null) {
    		throw new AlmadrabaAuthenticationException("body method");
    	}
    	
    	// TODO move "getSessionFactory()" to init
    	Session session = AlmadrabaContextListener.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		int number = params.getNumber();
		QueryType type = params.getQueryType();
		

		// 1st select the users as a subquery	
		ProjectionList pl = Projections.projectionList()
				.add(Projections.property("id.userId"))
				.add(Projections.sqlProjection("row_number() over() as rownumber", new String[] {"rownum"}, new Type[] { new IntegerType() }));
		DetachedCriteria subCriteria = DetachedCriteria.forClass(UserPageRankEvolution.class)
				.addOrder(Property.forName("rank").desc())
				.add(Property.forName("id.rankExecId").eq(new String("full")))
				.add(Property.forName("id.rankStepId").eq(new String("rank")))
				.add(Restrictions.sqlRestriction("rownum <= 5"))
				.setProjection(pl);
		
		Criteria criteria = session.createCriteria(UserPageRankEvolution.class)
				.add(Subqueries.propertyIn("id.userId", subCriteria))
				.add(Property.forName("id.rankExecId").eq(new String("full")));
		@SuppressWarnings("unchecked")
		List<UserPageRankEvolution> rows = criteria.list();
		System.out.println(rows.size());


        return null;
    }
}

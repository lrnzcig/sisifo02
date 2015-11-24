package xre;

import xre.AlmadrabaChartParams.QueryType;

/**
 * For initializing combos
 * 
 * Test client needs to implement this interface in a separate class to the server
 * (The server initializes values and might need to go to the database) 
 * 
 * @author lorenzorubio
 *
 */
public interface IAlmadrabaParamsArray {

	/**
	 * e.g. TOP, FOLLOWING
	 * 
	 * @return
	 */
	public QueryType[] getQueryTypes();

	public void setQueryTypes(final QueryType[] queryTypes);

	/**
	 * Different executions of page rank batch
	 * 
	 * @return
	 */
	public String[] getExecutionLabels();

	public void setExecutionLabels(final String[] executionLabels);	
}

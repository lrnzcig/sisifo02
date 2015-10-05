package xre;

import xre.AlmadrabaChartParams.QueryType;

public interface IAlmadrabaParamsArray {

	public QueryType[] getQueryTypes();

	public void setQueryTypes(final QueryType[] queryTypes);

	public String[] getExecutionLabels();

	public void setExecutionLabels(final String[] executionLabels);

}

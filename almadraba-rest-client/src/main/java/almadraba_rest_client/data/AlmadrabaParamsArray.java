package almadraba_rest_client.data;

import xre.AlmadrabaChartParams.QueryType;
import xre.IAlmadrabaParamsArray;

public class AlmadrabaParamsArray implements IAlmadrabaParamsArray {

	private QueryType[] queryTypes;
	private String[] executionLabels;
	
	public AlmadrabaParamsArray() {
		super();
	}

	@Override
	public QueryType[] getQueryTypes() {
		return queryTypes;
	}

	@Override
	public void setQueryTypes(final QueryType[] queryTypes) {
		this.queryTypes = queryTypes;
	}

	@Override
	public String[] getExecutionLabels() {
		return executionLabels;
	}

	@Override
	public void setExecutionLabels(final String[] executionLabels) {
		this.executionLabels = executionLabels;
	}

}

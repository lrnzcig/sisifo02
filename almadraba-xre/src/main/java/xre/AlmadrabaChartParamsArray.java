package xre;

import java.util.Arrays;

import xre.AlmadrabaChartParams.QueryType;

public class AlmadrabaChartParamsArray {
	private QueryType[] queryTypes;

	public AlmadrabaChartParamsArray() {
		super();
		for (QueryType e: QueryType.values()) {
			addQueryType(e);
		}
	}

	public QueryType[] getQueryTypes() {
		return queryTypes;
	}

	public void setQueryTypes(final QueryType[] queryTypes) {
		this.queryTypes = queryTypes;
	}

	public void addQueryType(final QueryType item) {
		if (this.queryTypes == null) {
			this.queryTypes = new QueryType[1];
		} else {
			int newLength = this.queryTypes.length + 1;
			this.queryTypes = Arrays.copyOf(this.queryTypes, newLength);
		}
		this.queryTypes[this.queryTypes.length - 1] = item;		
	}

	
	
}

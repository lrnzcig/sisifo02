package com.sisifo.almadraba_server.data;

import java.util.Arrays;

import xre.AlmadrabaChartParams.QueryType;
import xre.IAlmadrabaParamsArray;

public class AlmadrabaParamsArray implements IAlmadrabaParamsArray {
	private QueryType[] queryTypes;
	private String[] executionLabels;

	public AlmadrabaParamsArray() {
		super();
		for (QueryType e: QueryType.values()) {
			addQueryType(e);
		}
	}

	@Override
	public QueryType[] getQueryTypes() {
		return queryTypes;
	}

	@Override
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

	@Override
	public String[] getExecutionLabels() {
		return executionLabels;
	}

	@Override
	public void setExecutionLabels(final String[] executionLabels) {
		this.executionLabels = executionLabels;
	}

	
	
}

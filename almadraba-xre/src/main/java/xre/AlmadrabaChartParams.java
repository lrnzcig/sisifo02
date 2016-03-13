package xre;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class AlmadrabaChartParams {
	
	public enum QueryType {
		TOP,
		NEXT,
		SAME;
		
		private static Map<String, QueryType> namesMap = new HashMap<String, QueryType>();
		
		static {
			namesMap.put("top", QueryType.TOP);
			namesMap.put("next", QueryType.NEXT);
			namesMap.put("same", QueryType.SAME);
		}
		
		@JsonCreator
		public static QueryType forValue(final String value) {
			return namesMap.get(value);
		}
		
		@JsonValue
		public String toValue() {
			for (String key : namesMap.keySet()) {
				if (namesMap.get(key) == this) {
					return key;
				}
			}
			return null;
		}
	}
	
	private QueryType queryType;
	private String executionLabel;
	private int number;
	private String[] nonPinnedUsers;
	private String[] pinnedUsers;
	
	public AlmadrabaChartParams() {
		super();
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(final QueryType queryType) {
		this.queryType = queryType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public String getExecutionLabel() {
		return executionLabel;
	}

	public void setExecutionLabel(final String executionLabel) {
		this.executionLabel = executionLabel;
	}
	
	/**
	 * Last user id in the previous query
	 * 
	 * @return
	 */
	public String[] getNonPinnedUsers() {
		return this.nonPinnedUsers;
	}
	
	public void setNonPinnedUsers(final String[] nonPinnedUsers) {
		this.nonPinnedUsers = nonPinnedUsers;
	}

	/**
	 * User ids that have been pinned in the window (should be collected at any query)
	 * 
	 * @return
	 */
	public String[] getPinnedUsers() {
		return this.pinnedUsers;
	}
	
	public void setPinnedUsers(final String[] pinnedUsers) {
		this.pinnedUsers = pinnedUsers;
	}


	

}

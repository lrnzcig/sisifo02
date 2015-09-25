package xre;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class AlmadrabaChartParameters {
	
	public enum QueryType {
		TOP,
		FOLLOWING;
		
		private static Map<String, QueryType> namesMap = new HashMap<String, QueryType>();
		
		static {
			namesMap.put("top", QueryType.TOP);
			namesMap.put("following", QueryType.FOLLOWING);
		}
		
		@JsonCreator
		public static QueryType forValue(String value) {
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
	private int number;
	
	public AlmadrabaChartParameters() {
		super();
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}

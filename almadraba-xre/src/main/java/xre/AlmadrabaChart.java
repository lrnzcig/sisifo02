package xre;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class AlmadrabaChart {

	public enum UserType {
		TWITTER,
		GENERIC;
		
		private static Map<String, UserType> namesMap = new HashMap<String, UserType>();
		
		static {
			namesMap.put("twitter", UserType.TWITTER);
			namesMap.put("generic", UserType.GENERIC);
		}
		
		@JsonCreator
		public static UserType forValue(final String value) {
			if (namesMap.get(value) == null) {
				return GENERIC;
			}
			return namesMap.get(value);
		}
		
		@JsonValue
		public String toValue() {
			for (String key : namesMap.keySet()) {
				if (namesMap.get(key) == this) {
					return key;
				}
			}
			return "generic";
		}
	}


	private AlmadrabaSeries[] series;
	private Integer rankExecId;
	private String rankExecLabel;
	private Integer hourStep;
	private UserType userType;
	private String[] stepIds;
	
	public AlmadrabaChart() {
		super();
	}
	
	public AlmadrabaChart(final AlmadrabaSeries[] series, final Integer rankExecId, final String[] stepIds) {
		super();
		this.setSeries(series);
		this.setRankExecId(rankExecId);
		this.setStepIds(stepIds);
	}
	
	
	public AlmadrabaSeries[] getSeries() {
		return series;
	}

	public void setSeries(final AlmadrabaSeries[] series) {
		this.series = series;
	}

	public void addSeriesItem(final AlmadrabaSeries item) {
		if (this.series == null) {
			this.series = new AlmadrabaSeries[1];
		} else {
			int newLength = this.series.length + 1;
			this.series = Arrays.copyOf(this.series, newLength);
		}
		this.series[this.series.length - 1] = item;		
	}

	public String[] getStepIds() {
		return stepIds;
	}

	public void setStepIds(final String[] stepIds) {
		this.stepIds = stepIds;
	}

	public void addStepIdItem(final String item) {
		if (this.stepIds != null && Arrays.asList(this.stepIds).contains(item)) {
			return;
		}
		if (this.stepIds == null) {
			this.stepIds = new String[1];
		} else {
			int newLength = this.stepIds.length + 1;
			this.stepIds = Arrays.copyOf(this.stepIds, newLength);
		}
		this.stepIds[this.stepIds.length - 1] = item;		
	}

	public Integer getRankExecId() {
		return rankExecId;
	}

	public void setRankExecId(final Integer rankExecId) {
		this.rankExecId = rankExecId;
	}

	public String getRankExecLabel() {
		return rankExecLabel;
	}

	public void setRankExecLabel(final String rankExecLabel) {
		this.rankExecLabel = rankExecLabel;
	}

	public Integer getHourStep() {
		return hourStep;
	}

	public void setHourStep(final Integer hourStep) {
		this.hourStep = hourStep;
	}
	
	public AlmadrabaSeries getSeries(final BigInteger order) {
		for (AlmadrabaSeries s : series) {
			if (s.getRowNumber() == order) {
				return s;
			}
		}
		return null;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(final UserType userType) {
		this.userType = userType;
	}
	
}

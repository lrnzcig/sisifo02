package xre;

import java.math.BigInteger;
import java.util.Arrays;

public class AlmadrabaChart {

	private AlmadrabaSeries[] series;
	private Integer rankExecId;
	private String rankExecLabel;
	private Integer hourStep;
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

}

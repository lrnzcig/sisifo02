package xre;

import java.util.Arrays;

public class AlmadrabaChart {

	private AlmadrabaSeries[] series;
	private String rankExecId;
	private String[] stepIds;
	
	public AlmadrabaChart() {
		super();
	}
	
	public AlmadrabaChart(final AlmadrabaSeries[] series, final String rankExecId, final String[] stepIds) {
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
		if (this.stepIds == null) {
			this.stepIds = new String[1];
		} else {
			int newLength = this.stepIds.length + 1;
			this.stepIds = Arrays.copyOf(this.stepIds, newLength);
		}
		this.stepIds[this.stepIds.length - 1] = item;		
	}

	public String getRankExecId() {
		return rankExecId;
	}

	public void setRankExecId(final String rankExecId) {
		this.rankExecId = rankExecId;
	}

}

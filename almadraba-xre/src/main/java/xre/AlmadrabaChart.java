package xre;

import java.util.Arrays;

public class AlmadrabaChart {

	private AlmadrabaSeries[] series;
	
	public AlmadrabaChart() {
		super();
	}
	
	public AlmadrabaChart(AlmadrabaSeries[] series) {
		super();
		this.setSeries(series);
	}
	
	
	public AlmadrabaSeries[] getSeries() {
		return series;
	}

	public void setSeries(AlmadrabaSeries[] series) {
		this.series = series;
	}

	public void addSeriesItem(AlmadrabaSeries item) {
		if (this.series == null) {
			this.series = new AlmadrabaSeries[1];
		} else {
			int newLength = this.series.length + 1;
			this.series = Arrays.copyOf(this.series, newLength);
		}
		this.series[this.series.length - 1] = item;		
	} 


}

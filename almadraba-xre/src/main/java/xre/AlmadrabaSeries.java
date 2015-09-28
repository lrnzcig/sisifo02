package xre;

import java.math.BigInteger;
import java.util.Arrays;

public class AlmadrabaSeries {
	
	private BigInteger userId;
	private Float[] series;
	
	public AlmadrabaSeries() {
		super();
	}

	public AlmadrabaSeries(final BigInteger userId, final String rankExecId, final String rankStepId, final Float[] series) {
		super();
		this.userId = userId;
		this.series = series;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public Float[] getSeries() {
		return series;
	}

	public void setSeries(final Float[] series) {
		this.series = series;
	}
	
	public void addItemToSeries(final Float item) {
		if (this.series == null) {
			this.series = new Float[1];
		} else {
			int newLength = this.series.length + 1;
			this.series = Arrays.copyOf(this.series, newLength);
		}
		this.series[this.series.length - 1] = item;		
	}

	
}

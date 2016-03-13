package xre;

import java.math.BigInteger;
import java.util.Arrays;

public class AlmadrabaSeries {
	
	private String userId;
	private Float[] series;
	private BigInteger rowNumber;
	
	public AlmadrabaSeries() {
		super();
	}

	public AlmadrabaSeries(final String userId, final Float[] series) {
		super();
		this.userId = userId;
		this.series = series;
	}

	public AlmadrabaSeries(final String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
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

	public BigInteger getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(final BigInteger rowNumber) {
		this.rowNumber = rowNumber;
	}

}

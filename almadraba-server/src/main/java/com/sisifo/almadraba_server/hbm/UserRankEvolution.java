package com.sisifo.almadraba_server.hbm;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_rank_evolution")
public class UserRankEvolution {
	
	@Id
	private UserRankEvolutionId id;
	private Float rank;
	
	// following field comes from the ordered join; if the object is informed by hibernate it will fail
	// this gives the rank ordering to the GUI
	private BigInteger rowNumber;
	
	public UserRankEvolution() {
		super();
	}

	public UserRankEvolution(final UserRankEvolutionId id, final Float rank) {
		super();
		this.id = id;
		this.rank = rank;
	}

	public UserRankEvolutionId getId() {
		return id;
	}

	public void setId(final UserRankEvolutionId id) {
		this.id = id;
	}

	public Float getRank() {
		return rank;
	}

	public void setRank(final Float rank) {
		this.rank = rank;
	}

	public BigInteger getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(final BigInteger rowNumber) {
		this.rowNumber = rowNumber;
	}

}

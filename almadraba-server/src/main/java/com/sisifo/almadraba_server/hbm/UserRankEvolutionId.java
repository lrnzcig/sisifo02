package com.sisifo.almadraba_server.hbm;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;


public class UserRankEvolutionId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4029780045025241802L;
	
	@Column(name="user_id")
	private BigInteger userId;
	@Column(name="rank_exec_id")
	private Integer rankExecId;
	@Column(name="step_order")
	private Integer stepOrder;

	public UserRankEvolutionId() {
		super();
	}
		
	public UserRankEvolutionId(final BigInteger userId, final Integer rankExecId,
			final Integer stepOrder) {
		super();
		this.userId = userId;
		this.rankExecId = rankExecId;
		this.stepOrder = stepOrder;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public Integer getRankExecId() {
		return rankExecId;
	}

	public void setRankExecId(final Integer rankExecId) {
		this.rankExecId = rankExecId;
	}

	public Integer getStepOrder() {
		return stepOrder;
	}

	public void setStepOrder(final Integer stepOrder) {
		this.stepOrder = stepOrder;
	}

	
}

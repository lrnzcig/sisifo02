package com.sisifo.almadraba_server.hbm;

import java.io.Serializable;
import java.math.BigInteger;


public class UserPageRankEvolutionId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4029780045025241802L;
	
	private BigInteger userId;
	private String rankExecId;
	private String rankStepId;

	public UserPageRankEvolutionId() {
		super();
	}
		
	public UserPageRankEvolutionId(BigInteger userId, String rankExecId, String rankStepId) {
		super();
		this.userId = userId;
		this.rankExecId = rankExecId;
		this.rankStepId = rankStepId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getRankExecId() {
		return rankExecId;
	}

	public void setRankExecId(String rankExecId) {
		this.rankExecId = rankExecId;
	}

	public String getRankStepId() {
		return rankStepId;
	}

	public void setRankStepId(String rankStepId) {
		this.rankStepId = rankStepId;
	}

	
}

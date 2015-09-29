package com.sisifo.almadraba_server.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_page_rank_exec_step")
public class UserPageRankExecStep {

	@Id
	private Integer id;
	@Column(name="rank_exec_id")
	private Integer rankExecId;
	@Column(name="rank_step_label")
	private String rankStepLabel;
	@Column(name="step_order")
	private Integer stepOrder;
	@Column(name="step_timestamp")
	private Date stepTimestamp;
	
	public UserPageRankExecStep() {
		super();
	}

	public UserPageRankExecStep(final Integer id, final Integer rankExecId, final String rankStepLabel, final Integer stepOrder,
			final Date stepTimestamp) {
		super();
		this.id = id;
		this.rankExecId = rankExecId;
		this.rankStepLabel = rankStepLabel;
		this.stepOrder = stepOrder;
		this.stepTimestamp = stepTimestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getRankExecId() {
		return rankExecId;
	}

	public void setRankExecId(final Integer rankExecId) {
		this.rankExecId = rankExecId;
	}

	public String getRankStepLabel() {
		return rankStepLabel;
	}

	public void setRankStepLabel(final String rankStepLabel) {
		this.rankStepLabel = rankStepLabel;
	}

	public Integer getStepOrder() {
		return stepOrder;
	}

	public void setStepOrder(final Integer stepOrder) {
		this.stepOrder = stepOrder;
	}

	public Date getStepTimestamp() {
		return stepTimestamp;
	}

	public void setStepTimestamp(final Date stepTimestamp) {
		this.stepTimestamp = stepTimestamp;
	}
	
	
	
}

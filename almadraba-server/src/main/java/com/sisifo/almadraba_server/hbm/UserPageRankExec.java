package com.sisifo.almadraba_server.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_page_rank_exec")
public class UserPageRankExec {
	
	@Id
	private Integer id;
	@Column(name="rank_exec_label")
	private String rankExecLabel;
	@Column(name="hour_step")
	private Integer hourStep;

	public UserPageRankExec() {
		super();
	}

	public UserPageRankExec(final Integer id, final String rankExecLabel, final Integer hourStep) {
		super();
		this.id = id;
		this.rankExecLabel = rankExecLabel;
		this.hourStep = hourStep;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
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
	
	


}

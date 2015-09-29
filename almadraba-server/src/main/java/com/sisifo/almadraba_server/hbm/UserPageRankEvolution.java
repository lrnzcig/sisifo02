package com.sisifo.almadraba_server.hbm;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_page_rank_evolution")
public class UserPageRankEvolution {
	
	@Id
	private UserPageRankEvolutionId id;
	private Float rank;
	
	public UserPageRankEvolution() {
		super();
	}

	public UserPageRankEvolution(final UserPageRankEvolutionId id, final Float rank) {
		super();
		this.id = id;
		this.rank = rank;
	}

	public UserPageRankEvolutionId getId() {
		return id;
	}

	public void setId(final UserPageRankEvolutionId id) {
		this.id = id;
	}

	public Float getRank() {
		return rank;
	}

	public void setRank(final Float rank) {
		this.rank = rank;
	}

}

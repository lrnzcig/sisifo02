package com.sisifo.almadraba_server.hbm;

public class UserPageRankEvolution {
	
	private UserPageRankEvolutionId id;
	private Float rank;
	
	public UserPageRankEvolution() {
		super();
	}

	public UserPageRankEvolution(UserPageRankEvolutionId id, Float rank) {
		super();
		this.id = id;
		this.rank = rank;
	}

	public UserPageRankEvolutionId getId() {
		return id;
	}

	public void setId(UserPageRankEvolutionId id) {
		this.id = id;
	}

	public Float getRank() {
		return rank;
	}

	public void setRank(Float rank) {
		this.rank = rank;
	}

}

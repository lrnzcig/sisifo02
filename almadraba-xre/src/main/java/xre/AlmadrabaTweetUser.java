package xre;

import java.math.BigInteger;

public class AlmadrabaTweetUser {
	
	private BigInteger userId;
	private Integer statusesCount;
	private Integer friendsCount;
	private Integer followersCount;
	private String notoriousTweetText;
	
	public Integer getStatusesCount() {
		return statusesCount;
	}
	
	public void setStatusesCount(final Integer statusesCount) {
		this.statusesCount = statusesCount;
	}
	
	public Integer getFriendsCount() {
		return friendsCount;
	}
	
	public void setFriendsCount(final Integer friendsCount) {
		this.friendsCount = friendsCount;
	}
	
	public Integer getFollowersCount() {
		return followersCount;
	}
	
	public void setFollowersCount(final Integer followersCount) {
		this.followersCount = followersCount;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public String getNotoriousTweetText() {
		return notoriousTweetText;
	}

	public void setNotoriousTweetText(final String notoriousTweetText) {
		this.notoriousTweetText = notoriousTweetText;
	}
	
	

}

package xre;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import xre.AlmadrabaChart.UserType;

public class AlmadrabaUser {
	
	public static final String TWITTER_FOLLOWERS_COUNT_ATTR = "followers count";
	public static final String TWITTER_FRIENDS_COUNT_ATTR = "friends count";
	public static final String TWITTER_STATUSES_COUNT_ATTR = "statuses count";
	public static final String TWITTER_NOTORIOUS_TWEET_ATTR = "notorious tweet";
	
	public static final String GENERIC_GLOBAL_RANK_ATTR = "global rank";
	
	private BigInteger userId;
	private String userPublicName;
	private UserType userType;
	private Map<String, String> attributes;
	
	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(final Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(final String key, final Object value) {
		if (this.attributes == null) {
			this.attributes = new HashMap<>();
		}
		this.attributes.put(key, value.toString());
	}
	
	public String getAttribute(final String key) {
		if (this.attributes == null) {
			return null;
		}
		return this.attributes.get(key);
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(final UserType userType) {
		this.userType = userType;
	}

	public String getUserPublicName() {
		return userPublicName;
	}

	public void setUserPublicName(final String userPublicName) {
		this.userPublicName = userPublicName;
	}
	
}

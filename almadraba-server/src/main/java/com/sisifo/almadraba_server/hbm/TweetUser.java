package com.sisifo.almadraba_server.hbm;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tuser")
public class TweetUser {
	
	@Id
	private BigInteger id;
	private Date createdAt;
	private Integer contributorsEnabled;
	private String description;
	private Integer favouritesCount;
	private Integer followersCount;
	private Integer friendsCount;
	private Integer isTranslator;
	private Integer listedCount;
	private String location;
	private String name;
	private Integer _protected;
	private String screenName;
	private Integer statusesCount;
	private String url;
	private Integer verified;
	private Integer withheld;
	public BigInteger getId() {
		return id;
	}
	public void setId(final BigInteger id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}
	public Integer getContributorsEnabled() {
		return contributorsEnabled;
	}
	public void setContributorsEnabled(final Integer contributorsEnabled) {
		this.contributorsEnabled = contributorsEnabled;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public Integer getFavouritesCount() {
		return favouritesCount;
	}
	public void setFavouritesCount(final Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}
	public Integer getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(final Integer friendsCount) {
		this.friendsCount = friendsCount;
	}
	public Integer getIsTranslator() {
		return isTranslator;
	}
	public void setIsTranslator(final Integer isTranslator) {
		this.isTranslator = isTranslator;
	}
	public Integer getListedCount() {
		return listedCount;
	}
	public void setListedCount(final Integer listedCount) {
		this.listedCount = listedCount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(final String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public Integer get_protected() {
		return _protected;
	}
	public void set_protected(final Integer _protected) {
		this._protected = _protected;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(final String screeenName) {
		this.screenName = screeenName;
	}
	public Integer getStatusesCount() {
		return statusesCount;
	}
	public void setStatusesCount(final Integer statusesCount) {
		this.statusesCount = statusesCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(final String url) {
		this.url = url;
	}
	public Integer getVerified() {
		return verified;
	}
	public void setVerified(final Integer verified) {
		this.verified = verified;
	}
	public Integer getWithheld() {
		return withheld;
	}
	public void setWithheld(final Integer withheld) {
		this.withheld = withheld;
	}
	public Integer getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(final Integer followersCount) {
		this.followersCount = followersCount;
	}
	

	
}

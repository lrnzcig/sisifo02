package com.sisifo.almadraba_server.hbm;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tweet")
public class Tweet {

	@Id
	private BigInteger id;
	private Date createdAt;
	private Integer favoriteCount;
	private Integer inReplyToStatusId;
	private Integer inReplyToUserId;
	private String placeFullName;
	private Integer retweetCount;
	private BigInteger retweetedId;
	private String text;
	private String truncated;
	private BigInteger userId;
	private BigInteger retweetedUserId;
	private Boolean retweet;
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
	public Integer getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(final Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public Integer getInReplyToStatusId() {
		return inReplyToStatusId;
	}
	public void setInReplyToStatusId(final Integer inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}
	public Integer getInReplyToUserId() {
		return inReplyToUserId;
	}
	public void setInReplyToUserId(final Integer inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}
	public String getPlaceFullName() {
		return placeFullName;
	}
	public void setPlaceFullName(final String placeFullName) {
		this.placeFullName = placeFullName;
	}
	public Integer getRetweetCount() {
		return retweetCount;
	}
	public void setRetweetCount(final Integer retweetCount) {
		this.retweetCount = retweetCount;
	}
	public BigInteger getRetweetedId() {
		return retweetedId;
	}
	public void setRetweetedId(final BigInteger retweetedId) {
		this.retweetedId = retweetedId;
	}
	public String getText() {
		return text;
	}
	public void setText(final String text) {
		this.text = text;
	}
	public String getTruncated() {
		return truncated;
	}
	public void setTruncated(final String truncated) {
		this.truncated = truncated;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(final BigInteger userId) {
		this.userId = userId;
	}
	public BigInteger getRetweetedUserId() {
		return retweetedUserId;
	}
	public void setRetweetedUserId(final BigInteger retweetedUserId) {
		this.retweetedUserId = retweetedUserId;
	}
	public Boolean getRetweet() {
		return retweet;
	}
	public void setRetweet(final Boolean retweet) {
		this.retweet = retweet;
	}
	
	
	
}

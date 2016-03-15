package com.sisifo.almadraba_server.hbm;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sisifo.almadraba_server.data.IAlmadrabaUser;

@Entity
@Table(name="generic_user")
public class GenericUser implements IAlmadrabaUser {

	@Id
	@Column(name="user_id")
	private BigInteger id;
	@Column(name="user_name")
	private String userName;
	@Column(name="global_rank")
	private Integer globalRank;
	@Column(name="other_data")
	private String otherData;
	
	public GenericUser() {
		super();
	}

	public GenericUser(final BigInteger id, final String userName, final Integer globalRank, final String other_data) {
		super();
		this.id = id;
		this.userName = userName;
		this.globalRank = globalRank;
		this.otherData = other_data;
	}

	@Override
	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public Integer getGlobalRank() {
		return globalRank;
	}

	public void setGlobalRank(final Integer globalRank) {
		this.globalRank = globalRank;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(final String other_data) {
		this.otherData = other_data;
	}

	public Map<String, String> getOtherDataAttributes() {
		// TODO a bit weird to read JSON so that it goes into the pojo and Jackson converts it back to json...
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		Type typeOfMap = new TypeToken<Map<String, String>>(){}.getType();
		return gson.fromJson(this.otherData, typeOfMap);
	}

}

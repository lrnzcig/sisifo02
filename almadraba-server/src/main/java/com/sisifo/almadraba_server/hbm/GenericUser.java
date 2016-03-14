package com.sisifo.almadraba_server.hbm;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sisifo.almadraba_server.data.IAlmadrabaUser;

@Entity
@Table(name="generic_user")
public class GenericUser implements IAlmadrabaUser {

	@Id
	@Column(name="user_id")
	private BigInteger id;
	@Column(name="user_name")
	private String userName;
	@Column(name="other_data")
	private String otherData;
	
	public GenericUser() {
		super();
	}

	public GenericUser(final BigInteger id, final String userName, final String other_data) {
		super();
		this.id = id;
		this.userName = userName;
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

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(final String other_data) {
		this.otherData = other_data;
	}

}

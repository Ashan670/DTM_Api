package com.payable.ttt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tbl_corp_user_login_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMUserLoginHistory {

	public static final int STATUS_SUCCESS_LOGIN = 1;
	public static final int STATUS_SUCCESS_LOGOUT = 2;
	public static final int STATUS_USER_NOT_EXISTS = 3;
	public static final int STATUS_INVALID_USERNAME = 4;
	public static final int STATUS_INVALID_PASSWORD = 5;
	public static final int STATUS_USER_DEACTIVATED = 6;
	public static final int STATUS_USER_NOT_ACTIVE = 7;
	public static final int STATUS_USER_ROLE_DEACTIVATED = 8;
	public static final int STATUS_USER_ROLE_NOT_ACTIVE = 9;
	public static final int STATUS_ACTIVE_USER_SESSION_ALREADY_EXISTS_WITH_SAME_CREDENTIALS_OR_IMPROPER_LOGOUT = 10;
	public static final int STATUS_FAILED_LOGOUT = 11;
	
	public static final int STATUS_TERMINAL_NOT_EXISTS = 13;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "username")
	private String username;

	@Column(name = "status")
	private int status;

	@Column(name = "src_ip")
	private String srcIp;

	@Column(name = "error_weight")
	private int errorWeight;

	@Column(name = "log_time")
	private Date logtime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public int getErrorWeight() {
		return errorWeight;
	}

	public void setErrorWeight(int errorWeight) {
		this.errorWeight = errorWeight;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

}

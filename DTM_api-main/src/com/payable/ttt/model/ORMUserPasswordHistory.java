package com.payable.ttt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_user_password_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMUserPasswordHistory {

	public static final int IS_DEFAULT_PASSWORD_NO = 0;
	public static final int IS_DEFAULT_PASSWORD_YES = 1;
	public static final int NO_OF_OLD_PASSWORD_CHECK_FOR_CHANGE = 4;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "pwd")
	private String pwd;

	@Column(name = "is_default_password")
	private int isDefaultPassword;

	@Column(name = "recorded_ts")
	private Date recordedTs;

	public ORMUserPasswordHistory() {
		super();
	}
	
	public ORMUserPasswordHistory(String userId, String pwd, int isDefaultPassword, Date recordedTs) {
		super();
		this.userId = userId;
		this.pwd = pwd;
		this.isDefaultPassword = isDefaultPassword;
		this.recordedTs = recordedTs;
	}
	
	

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getIsDefaultPassword() {
		return isDefaultPassword;
	}

	public void setIsDefaultPassword(int isDefaultPassword) {
		this.isDefaultPassword = isDefaultPassword;
	}

	public Date getRecordedTs() {
		return recordedTs;
	}

	public void setRecordedTs(Date recordedTs) {
		this.recordedTs = recordedTs;
	}

}

package com.payable.ttt.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMUser {

	public static final int STATUS_ACTIVATED = 1;
	public static final int STATUS_DEACTIVATED = 2;
	public static final int STATUS_BLOCKED = 3;

	public static final int IS_DEFAULT_PASSWORD_NO = 0;
	public static final int IS_DEFAULT_PASSWORD_YES = 1;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "password")
	private String password;

	@Column(name = "salt")
	private String salt;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private int status;

	@Column(name = "last_ip")
	private String last_ip;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")

	private String createdBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "auth")
	private long auth;

	@Column(name = "is_default_password")
	private int isDefaultPassword;

	@Column(name = "last_logged_ts")
	private long lastLoggedTs;

	@Column(name = "expiry")
	private long expiry;

	
	@Column(name = "user_role_id", insertable = false, updatable = false)
	private String userRoleId;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_role_id", nullable = false)
	private ORMUserRole userRole;
	
	
	@Column(name = "user_group_id", insertable = false, updatable = false)
	private String userGroupId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_group_id", nullable = false)
	private ORMUserGroup userGroup;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLast_ip() {
		return last_ip;
	}

	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public long getAuth() {
		return auth;
	}

	public void setAuth(long auth) {
		this.auth = auth;
	}

	public int getIsDefaultPassword() {
		return isDefaultPassword;
	}

	public void setIsDefaultPassword(int isDefaultPassword) {
		this.isDefaultPassword = isDefaultPassword;
	}

	public long getLastLoggedTs() {
		return lastLoggedTs;
	}

	public void setLastLoggedTs(long lastLoggedTs) {
		this.lastLoggedTs = lastLoggedTs;
	}

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	public ORMUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(ORMUserRole userRole) {
		this.userRole = userRole;
	}

	public ORMUserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(ORMUserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	
	
	

}

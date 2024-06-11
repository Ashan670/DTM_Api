package com.payable.ttt.dto;

public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String password;  // Consider handling passwords more securely
    private String salt;      // Salt for password hashing
    private String email;
    private int status;
    private String lastIp;
    private String createdBy;
    private String updatedBy;
    private long auth;
    private int isDefaultPassword;
    private long lastLoggedTs;
    private long expiry;
    private String userRoleId;
    private String userGroupId;
    
	public UserDTO(String id, String firstName, String lastName, String password, String salt, String email, int status,
			String lastIp, String createdBy, String updatedBy, long auth, int isDefaultPassword, long lastLoggedTs,
			long expiry, String userRoleId, String userGroupId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.salt = salt;
		this.email = email;
		this.status = status;
		this.lastIp = lastIp;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.auth = auth;
		this.isDefaultPassword = isDefaultPassword;
		this.lastLoggedTs = lastLoggedTs;
		this.expiry = expiry;
		this.userRoleId = userRoleId;
		this.userGroupId = userGroupId;
	}
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
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

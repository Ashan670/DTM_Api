package com.payable.ttt.dto.userrole;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payable.ttt.dto.BaseModel;

import com.payable.ttt.model.ORMUserRole;

public class UserRoleDTO extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 202212181213L;
	
	@Expose
	String id;
	
	@Expose
	@NotBlank(message = "Role Name is blank")
	@NotNull(message = "Role Name is null")
	@Size(min = 1, max = 30, message = " Name should be more than 1 character and less than 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\_\\ ]*$", message = "Please enter valid data for name")
	@SerializedName("roleName")
	String role_name;
	
	@Expose
	@Size(max = 30, message = "Description should be less than 30 characters")
	@Pattern(regexp = "^[a-zA-Z\\_\\ ]*$", message = "Please enter valid data for role description.")
	String description;
	@Expose
	int status;
	

	public UserRoleDTO() {
		super();
	}
	
	public UserRoleDTO(ORMUserRole orm) {
		super();
		this.id = orm.getId();
		this.role_name = orm.getRoleName();
		this.description = orm.getDescription();
		this.status = orm.getStatus();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


}

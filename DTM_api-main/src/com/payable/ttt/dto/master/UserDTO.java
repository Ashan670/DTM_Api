package com.payable.ttt.dto.master;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payable.ttt.dto.BaseModel;
import com.payable.ttt.model.ORMProject;
import com.payable.ttt.model.ORMUser;

public class UserDTO extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 202212181213L;
	
	@Expose
	String id;
	
	@Expose
	@SerializedName("firstName")
	String firstName;
	
	@Expose
	@SerializedName("lastName")
	String lastName;
	
	@Expose
	@SerializedName("email")
	String email;

	@Expose
	int status;
	

	public UserDTO() {
		super();
	}
	
	public UserDTO(ORMUser orm) {
		super();
		this.id = orm.getId();
		this.firstName = orm.getFirstName();
		this.lastName = orm.getLastName();
		this.email = orm.getEmail();
		this.status = orm.getStatus();
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
	
	

}

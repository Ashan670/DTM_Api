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
import com.payable.ttt.model.ORMTaskType;
import com.payable.ttt.model.ORMUserRole;

public class TaskTypeDTO extends BaseModel {

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
	@SerializedName("name")
	String name;
	
	
	

	@Expose
	int status;
	

	public TaskTypeDTO() {
		super();
	}
	
	public TaskTypeDTO(ORMTaskType orm) {
		super();
		this.id = orm.getId();
		this.name = orm.getName();
		this.status = orm.getStatus();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


}

package com.payable.ttt.model;

import java.util.Date;



import javax.persistence.Column;
import javax.persistence.Entity;


import javax.persistence.Id;

import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.payable.ttt.dto.userrole.UserRoleDTO;

@Entity
@Table(name = "t_project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMProject {

	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 2;

	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "pro_name")
	private String name;

	@Column(name = "status")
	private int status;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




}

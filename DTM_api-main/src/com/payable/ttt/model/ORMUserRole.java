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
@Table(name = "t_user_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMUserRole {

	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 2;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private int status;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

	public ORMUserRole() {
		
	}
	
	
	public ORMUserRole(UserRoleDTO dto, String userId) {
		super();
		
		if (dto.getId() == null) {
//			UUID uuid = Generators.timeBasedGenerator().generate();
//			this.id=uuid.toString();
			this.createdAt = new Date();
			this.createdBy = userId;
		}else {
			this.id = dto.getId();
		}
		if (dto.getRole_name().length() > 5)
			this.roleName = dto.getRole_name();
		this.description = dto.getDescription();
		this.status = dto.getStatus();
		if (dto.getId() == null) {
			this.createdAt = new Date();
			this.createdBy = userId;
		}
		this.status=STATUS_ACTIVE;
		this.updatedAt = new Date();
		this.updatedBy = userId;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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



}

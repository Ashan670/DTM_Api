package com.payable.ttt.model;

import java.util.Date;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.payable.ttt.dto.userrole.UserRoleDTO;

@Entity
@Table(name = "t_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMCategory {

	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 2;

	
	@Id
	@Column(name = "id")
	private String id;
	
	
	@Column(name = "pro_id", insertable = false, updatable = false)
	private String proId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_id")
	private ORMProject project;

	
	@Column(name = "cat_name")
	private String cat_name;

	@Column(name = "status")
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public ORMProject getProject() {
		return project;
	}

	public void setProject(ORMProject project) {
		this.project = project;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

}

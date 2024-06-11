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
@Table(name = "t_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMActivity {

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
	
	@Column(name = "cat_id", insertable = false, updatable = false)
	private String catId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cat_id")
	private ORMCategory category;

	
	@Column(name = "act_name")
	private String act_name;

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

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public ORMCategory getCategory() {
		return category;
	}

	public void setCategory(ORMCategory category) {
		this.category = category;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

}

package com.payable.ttt.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



@Entity
@Table(name = "t_task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMTask {
	
	public static final int STATUS_ACTIVATED = 1;
	public static final int STATUS_ON_HOLD = 2;
	public static final int STATUS_DEACTIVATED = 3;
	
	@Id
	@Column(name = "id")
	@SerializedName("id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Expose
	private String id;
	
	private String user_id;
	
	private String tasktype_id;
	
	private String pro_id;
	
	private String cat_id;
	
	private String act_id;
	
	private String task_detail;
	
	private int is_active;
	
	private Date init_ts;
	
	private String bug_id;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPro_id() {
		return pro_id;
	}

	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getTask_detail() {
		return task_detail;
	}

	public void setTask_detail(String task_detail) {
		this.task_detail = task_detail;
	}

	public int getIs_active() {
		return is_active;
	}

	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}

	public Date getInit_ts() {
		return init_ts;
	}

	public void setInit_ts(Date init_ts) {
		this.init_ts = init_ts;
	}

	public String getTasktype_id() {
		return tasktype_id;
	}

	public void setTasktype_id(String tasktype_id) {
		this.tasktype_id = tasktype_id;
	}

	public String getBug_id() {
		return bug_id;
	}

	public void setBug_id(String bug_id) {
		this.bug_id = bug_id;
	}


	
	
	

}

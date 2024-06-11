package com.payable.ttt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "valltask")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ORMRecentTask {
	
	@Id
	@Column(name = "uniquid")
	String uniquid;

	@Column(name = "id")
	String id;
	
	@Column(name = "user_id")
	String user_id;
	
	@Column(name = "tasktype_id")
	String tasktype_id;
	
	@Column(name = "tasktype_name")
	String tasktype_name;
	
	@Column(name = "pro_id")
	String pro_id;
	
	@Column(name = "pro_name")
	String pro_name;
	
	@Column(name = "cat_id")
	String cat_id;
	
	@Column(name = "cat_name")
	String cat_name;
	
	@Column(name = "act_id")
	String act_id;
	
	@Column(name = "act_name")
	String act_name;
	
	@Column(name = "task_detail")
	String task_detail;
	
	@Column(name = "is_active")
	int is_active;
	
	@Column(name = "init_ts")
	Date init_ts;
	
	@Column(name = "bug_id")
	String bug_id;
	
	
//	
//	@Column(name = "startTimeSystem")
//	Date startTimeSystem;
	
	@Column(name = "startTimeEnterd")
	Date startTimeEnterd;
	
//	@Column(name = "init_ts")
//	String init_ts;
	
	@Column(name = "startTimeSystem")
	Date startTimeSystem;
	
//	@Column(name = "startTimeSystem")
//	String startTimeSystem;
	
	
	@Column(name = "endTimeSystem")
	Date endTimeSystem;
	
//	@Column(name = "endTimeSystem")
//	String endTimeSystem;
	
	@Column(name = "endTimeEnterd")
	Date endTimeEnterd;


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

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String act_name) {
		this.act_name = act_name;
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


	


//	public Date getStartTimeSystem() {
//		return startTimeSystem;
//	}
//
//
//	public void setStartTimeSystem(Date startTimeSystem) {
//		this.startTimeSystem = startTimeSystem;
//	}


	public String getUniquid() {
		return uniquid;
	}

	public void setUniquid(String uniquid) {
		this.uniquid = uniquid;
	}

	public Date getInit_ts() {
		return init_ts;
	}

	public void setInit_ts(Date init_ts) {
		this.init_ts = init_ts;
	}

	public Date getStartTimeEnterd() {
		return startTimeEnterd;
	}

	public void setStartTimeEnterd(Date startTimeEnterd) {
		this.startTimeEnterd = startTimeEnterd;
	}

	public Date getStartTimeSystem() {
		return startTimeSystem;
	}

	public void setStartTimeSystem(Date startTimeSystem) {
		this.startTimeSystem = startTimeSystem;
	}

	public Date getEndTimeSystem() {
		return endTimeSystem;
	}

	public void setEndTimeSystem(Date endTimeSystem) {
		this.endTimeSystem = endTimeSystem;
	}

	public Date getEndTimeEnterd() {
		return endTimeEnterd;
	}

	public void setEndTimeEnterd(Date endTimeEnterd) {
		this.endTimeEnterd = endTimeEnterd;
	}

	public String getTasktype_id() {
		return tasktype_id;
	}

	public void setTasktype_id(String tasktype_id) {
		this.tasktype_id = tasktype_id;
	}

	public String getTasktype_name() {
		return tasktype_name;
	}

	public void setTasktype_name(String tasktype_name) {
		this.tasktype_name = tasktype_name;
	}

	public String getBug_id() {
		return bug_id;
	}

	public void setBug_id(String bug_id) {
		this.bug_id = bug_id;
	}


	


	
	
	
}



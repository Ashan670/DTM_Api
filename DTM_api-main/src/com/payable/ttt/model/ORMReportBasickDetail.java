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
public class ORMReportBasickDetail {
	
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
	
	@Column(name = "startTimeSystem")
	Date startTimeSystem;
	
	@Column(name = "startTimeEnterd")
	Date startTimeEnterd;
	
	@Column(name = "endTimeSystem")
	Date endTimeSystem;
	
	
	@Column(name = "endTimeEnterd")
	Date endTimeEnterd;
	
	@Column(name = "first_name")
	String firstName;
	
	@Column(name = "last_name")
	String lastName;
	
	@Column(name = "user_group_id")
	String user_group_id;
	
	
	@Column(name = "group_name")
	String group_name;
	
	@Column(name = "user_role_id")
	String user_role_id;
	
	@Column(name = "role_name")
	String role_name;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "bug_id")
	String bug_id;

	long enterd_duration_seconds;
	
	long system_duration_seconds;
	
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


	public Date getInit_ts() {
		return init_ts;
	}


	public void setInit_ts(Date init_ts) {
		this.init_ts = init_ts;
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




	public Date getStartTimeEnterd() {
		return startTimeEnterd;
	}


	public void setStartTimeEnterd(Date startTimeEnterd) {
		this.startTimeEnterd = startTimeEnterd;
	}


	public Date getEndTimeEnterd() {
		return endTimeEnterd;
	}


	public void setEndTimeEnterd(Date endTimeEnterd) {
		this.endTimeEnterd = endTimeEnterd;
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


	public String getUser_group_id() {
		return user_group_id;
	}


	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}


	public String getGroup_name() {
		return group_name;
	}


	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}


	public String getUser_role_id() {
		return user_role_id;
	}


	public void setUser_role_id(String user_role_id) {
		this.user_role_id = user_role_id;
	}


	public String getRole_name() {
		return role_name;
	}


	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public long getEnterd_duration_seconds() {
		return enterd_duration_seconds;
	}


	public void setEnterd_duration_seconds(long enterd_duration_seconds) {
		this.enterd_duration_seconds = enterd_duration_seconds;
	}


	public long getSystem_duration_seconds() {
		return system_duration_seconds;
	}


	public void setSystem_duration_seconds(long system_duration_seconds) {
		this.system_duration_seconds = system_duration_seconds;
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


	
//	public long getEnterd_duration_minute() {
//		return enterd_duration_minute;
//	}
//
//
//	public void setEnterd_duration_minute(long enterd_duration_minute) {
//		this.enterd_duration_minute = enterd_duration_minute;
//	}
//
//
//	public long getSystem_duration_minute() {
//		return system_duration_minute;
//	}
//
//
//	public void setSystem_duration_minute(long system_duration_minute) {
//		this.system_duration_minute = system_duration_minute;
//	}
	
	
	
//    public long getDurationInMinutes() {
//        if (startTimeEnterd != null && endTimeEnterd != null) {
//            long durationMillis = endTimeEnterd.getTime() - startTimeEnterd.getTime();
//            return durationMillis / (1000 * 60); // Convert milliseconds to minutes
//        }
//        return 0;
//    }
	
	
	
}



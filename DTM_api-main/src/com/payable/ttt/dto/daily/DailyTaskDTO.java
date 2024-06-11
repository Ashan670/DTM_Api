package com.payable.ttt.dto.daily;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payable.ttt.dto.BaseModel;
import com.payable.ttt.model.ORMRecentTask;
import com.payable.ttt.util.DateUtil;


public class DailyTaskDTO  extends BaseModel {
	
	static long totalDurationInMillis = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5648973823417831731L;
	
	@Nullable
	@Size(max = 40, message = "id must be no more than 40 characters")
	@Expose
	private String id;
	
	@Nullable
	@Size(max = 40, message = "user id must be no more than 40 characters")
	@Expose
	@SerializedName("userId")
	private String user_id;

	@Nullable
	@Size(max = 40, message = "task type id must be no more than 40 characters")
	@Expose
	@SerializedName("taskTypeId")
	private String tasktype_id;

	@Nullable
	@Size(max = 100, message = "task type name must be no more than 100 characters")
	@Expose
	@SerializedName("taskTypeName")
	private String tasktype_name;

	@Nullable
	@Size(max = 40, message = "project id must be no more than 40 characters")
	@Expose
	@SerializedName("proId")
	private String pro_id;

	@Nullable
	@Size(max = 100, message = "project name must be no more than 100 characters")
	@Expose
	@SerializedName("projectName")
	private String pro_name;
	
	
	@Nullable
	@Size(max = 40, message = "category id must be no more than 40 characters")
	@Expose
	@SerializedName("catId")
	private String cat_id;

	@Nullable
	@Size(max = 100, message = "category  must be no more than 100 characters")
	@Expose
	@SerializedName("CategoryName")
	private String cat_name;
	
	@Nullable
	@Size(max = 40, message = "activity id must be no more than 40 characters")
	@Expose
	@SerializedName("actId")
	private String act_id;

	@Nullable
	@Size(max = 40, message = "activity must be no more than 40 characters")
	@Expose
	@SerializedName("activityName")
	private String act_name;
	
	@Nullable
	@Size(max = 250, message = "task detal be no more than 250 characters")
	@Expose
	@SerializedName("taskDetail")
	private String task_detail;

	@Nullable
	@Size(max = 25, message = "bugid must be no more than 25 characters")
	@Expose
	@SerializedName("bugId")
	private String bug_id;
	
	@Expose
	@SerializedName("status")
	private int is_active;

	@Expose
	@SerializedName("initTS")
	private Date init_ts;

	@Expose
	@SerializedName("systemStartTime")
	private String system_start_time;

	@Expose
	@SerializedName("systemEndTime")
	private String system_end_time;

	@Expose
	@SerializedName("enteredEndTime")
	private String enterd_end_time;
	
	@Expose
	@SerializedName("enterdStartTime")
	private String enterd_start_time;
	
	@Expose
	private String duration;
	
	
	@Expose
	@SerializedName("remark")
	private String remark;
	
	public DailyTaskDTO(ORMRecentTask orm) {
	    super();
	    this.id = orm.getId();
	    this.user_id = orm.getUser_id();
	    this.tasktype_id = orm.getTasktype_id();
	    this.tasktype_name = orm.getTasktype_name();
	    this.pro_id = orm.getPro_id();
	    this.pro_name = orm.getPro_name();
	    this.cat_id = orm.getCat_id();
	    this.cat_name = orm.getCat_name();
	    this.act_id = orm.getAct_id();
	    this.act_name = orm.getAct_name();
	    this.task_detail = orm.getTask_detail();
	    this.is_active = orm.getIs_active();
	    this.init_ts = orm.getInit_ts();
	    this.bug_id = orm.getBug_id();

	    this.system_start_time = DateUtil.getDateToDisplayString(orm.getStartTimeSystem());
	    if (orm.getEndTimeSystem() == null) {
	        this.system_end_time = "";
	    } else {
	        this.system_end_time = DateUtil.getDateToDisplayString(orm.getEndTimeSystem());
	    }

	    Date est = orm.getStartTimeEnterd();
	    this.enterd_start_time = DateUtil.getDateToDisplayString(est);

	    Date eet = orm.getEndTimeEnterd();
	    if (eet == null) {
	        this.enterd_end_time = "";
	    } else {
	        this.enterd_end_time = DateUtil.getDateToDisplayString(eet);
	    }

	    if (eet == null) {
	        eet = new Date();
	    }

	    long durationInMillis = eet.getTime() - est.getTime();
	    int hours = (int) (durationInMillis / (1000 * 60 * 60));
	    int minutes = (int) (durationInMillis / (1000 * 60)) % 60;
	    int seconds = (int) (durationInMillis / 1000) % 60;
	    duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);

	    totalDurationInMillis += durationInMillis;
	}

	
	public static String totalDuration() {
		int totalHours = (int) (totalDurationInMillis / (1000 * 60 * 60));
		int totalMinutes = (int) (totalDurationInMillis / (1000 * 60)) % 60;
		int totalSeconds = (int) (totalDurationInMillis / 1000) % 60;
		return String.format("%02d:%02d:%02d", totalHours, totalMinutes, totalSeconds);
	}
	
	public String getRemark() {
	   return remark;
	}

	public void setRemark(String remark) {
	    this.remark = remark;
	}
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
//	public Date getInit_ts() {
//		return init_ts;
//	}
//	public void setInit_ts(Date init_ts) {
//		this.init_ts = init_ts;
//	}
	
	
	public String getEnterd_end_time() {
		
		return enterd_end_time;
	}
//	public String getInit_ts() {
//		return init_ts;
//	}
//
//	public void setInit_ts(String init_ts) {
//		this.init_ts = init_ts;
//	}
	
	

	public void setEnterd_end_time(String enterd_end_time) {
		this.enterd_end_time = enterd_end_time;
	}
	public Date getInit_ts() {
		return init_ts;
	}

	public void setInit_ts(Date init_ts) {
		this.init_ts = init_ts;
	}

	public String getEnterd_start_time() {
		return enterd_start_time;
	}
	public void setEnterd_start_time(String enterd_start_time) {
		this.enterd_start_time = enterd_start_time;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getSystem_start_time() {
		return system_start_time;
	}
	public void setSystem_start_time(String system_start_time) {
		this.system_start_time = system_start_time;
	}
	public String getSystem_end_time() {
		return system_end_time;
	}
	public void setSystem_end_time(String system_end_time) {
		this.system_end_time = system_end_time;
	}

	public static long getTotalDurationInMillis() {
		return totalDurationInMillis;
	}

	public static void setTotalDurationInMillis(long totalDurationInMillis) {
		DailyTaskDTO.totalDurationInMillis = totalDurationInMillis;
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

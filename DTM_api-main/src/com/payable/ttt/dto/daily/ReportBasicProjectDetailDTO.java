package com.payable.ttt.dto.daily;

import java.text.ParseException;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payable.ttt.dto.BaseModel;

import com.payable.ttt.model.ORMReportBasickDetail;
import com.payable.ttt.util.DateUtil;

public class ReportBasicProjectDetailDTO extends BaseModel {

	static long totalDurationInMillis = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5648973823417831731L;

	@Expose
	private String id;

	@Expose
	@SerializedName("userId")
	private String user_id;

	@Expose
	@SerializedName("proId")
	private String pro_id;

	@Expose
	@SerializedName("projectName")
	private String pro_name;

	@Expose
	@SerializedName("catId")
	private String cat_id;

	@Expose
	@SerializedName("CategoryName")
	private String cat_name;

	@Expose
	@SerializedName("actId")
	private String act_id;

	@Expose
	@SerializedName("activityName")
	private String act_name;
	
	@Expose
	@SerializedName("taskTypeId")
	private String task_type_id;

	@Expose
	@SerializedName("taskTypeName")
	private String task_type_name;

	@Expose
	private String duration;

	@Expose
	private String staffName;

	
		public ReportBasicProjectDetailDTO() {
			super();
			
		
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


	public static long getTotalDurationInMillis() {
		return totalDurationInMillis;
	}
	
	


	public String getStaffName() {
		return staffName;
	}


	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}


	


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public static void setTotalDurationInMillis(long totalDurationInMillis) {
		ReportBasicProjectDetailDTO.totalDurationInMillis = totalDurationInMillis;
	}


	public String getTask_type_id() {
		return task_type_id;
	}


	public void setTask_type_id(String task_type_id) {
		this.task_type_id = task_type_id;
	}


	public String getTask_type_name() {
		return task_type_name;
	}


	public void setTask_type_name(String task_type_name) {
		this.task_type_name = task_type_name;
	}

}

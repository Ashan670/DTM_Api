package com.payable.ttt.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payable.ttt.config.SysConfigParam;


/*
 * @author  Amila Giragama
 * @version 1.0
 * @since   2022-11-18 
 * 
 */
public class CommonSearchRequest extends BaseModel {


	private static final long serialVersionUID = 309364797716680596L;

	@SerializedName("id")
	@Expose
	@Size(max = 100 ,message = "Id length can not be grater than or equal 100 ")
	private String Id;

	@SerializedName("pageId")
	@Expose
	@Min(value = 0, message = "pageId can not be less than 0 ")
	private int pageId;

	@SerializedName("pageSize")
	@Expose
	@Min(value = 1, message = "pageSize can not be less than or equal 0 ")
	@Max(value = SysConfigParam.MAX_PAGE_SIZE_LIST, message = "pageSize can not be grater than or equal  " + SysConfigParam.MAX_PAGE_SIZE_LIST)
	private int pageSize;

	@SerializedName("searchText")
	@Expose
	private String searchText;

	@SerializedName("status")
	@Expose
	private int status;
	
	
	private String passwordCurrent;
	
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#()�[{}]:;,?/*~$+=]).{10,100}$",
//	message = "Please enter a valid data for User Password")
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#?/*$+]).{7,100}$",
	message = "Please enter a valid data for User Password")
	private String passwordNew;
	//<>!^&
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#?/*$+]).{7,100}$",
			message = "Please enter a valid data for User Password")
	private String passwordConfirm;
	
	@SerializedName("pwdType")
	private int pwdType;
	
	
//	@NotNull(message= "srcIp cannot be null")
//	@NotBlank(message = "srcIp is empty")
	@Pattern(regexp = "^[a-zA-Z0-9.]*$" , message="Invalid srcIp")
	@Size(min = 1, max = 20 ,message = "srcIp invalid length")
	@SerializedName("srcIp")
	@Expose
	private String srcIp ;

	

	
	
	@Expose
	private String staffUserId;

	

	
	@SerializedName("sort")
	@Expose
	private int sort;
	
	@SerializedName("orderBy")
	@Expose
	private int orderBy;
	
	@SerializedName("sDate")
	@Expose
	private Date stDate;

	@SerializedName("eDate")
	@Expose
	private Date enDate;
	
	@SerializedName("isActive")
	@Expose
	private int isActive;
	
	
//	@SerializedName("taskTypeIdAbc")
//	@Expose
//	private String taskTypeIdABC;
	
	@SerializedName("taskTypeId")
	@Expose
	private String taskTypeId;
	
	@SerializedName("projectId")
	@Expose
	private String projectId;
	
	@SerializedName("categoryId")
	@Expose
	private String categoryId;
	
	@SerializedName("activityId")
	@Expose
	private String activityId;
	
	
	@SerializedName("userRoleId")
	@Expose
	private String userRoleId;
	
	@SerializedName("userGroupId")
	@Expose
	private String userGroupId;
	
	
	public static final String DEFAULT_ID = null;
	public static final int DEFAULT_PAGE_ID = -1;
	public static final int DEFAULT_PAGE_SIZE = -1;
	public static final String DEFAULT_SEARCH_TEXT = null;
	public static final int DEFAULT_STATUS = -1;
	public static final int ALL_STATUS = 0;
	public static final int ACTIVE_STATUS = 1;
	
	public static final int ORDER_ASC = 1;
	public static final int ORDER_DESC = 2;

	public static final int ORDER_BY_TIME = 1;
	public static final int ORDER_BY_TID = 2;
	public static final int ORDER_BY_USER_NAME = 3;

	public CommonSearchRequest() {
		Id = null;
		pageId = 0;
		pageSize = 1;
		searchText = null;
		status = -1;
		sort=ORDER_DESC;
		orderBy=ORDER_BY_TIME;
	}

	public String getId() {

		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}
	

	public String getPasswordCurrent() {
		return passwordCurrent;
	}

	public void setPasswordCurrent(String passwordCurrent) {
		this.passwordCurrent = passwordCurrent;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
		
	public int getPwdType() {
		return pwdType;
	}

	public void setPwdType(int pwdType) {
		this.pwdType = pwdType;
	}

	
	
	
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public Date getStDate() {
		return stDate;
	}

	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}

	public Date getEnDate() {
		return enDate;
	}

	public void setEnDate(Date enDate) {
		this.enDate = enDate;
	}

	
	
	
	
	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	
	public String getStaffUserId() {
		return staffUserId;
	}

	public void setStaffUserId(String staffUserId) {
		this.staffUserId = staffUserId;
	}
	

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Override
	public String toString() {
		StringBuilder csrDetail = new StringBuilder("CommonSearchRequest :: ");
		if (Id != DEFAULT_ID)
			csrDetail.append("[Id=" + Id + "]");
		if (pageId > DEFAULT_PAGE_ID)
			csrDetail.append("[pageId=" + pageId + "]");
		if (pageSize > DEFAULT_PAGE_SIZE)
			csrDetail.append("[pageSize=" + pageSize + "]");
		if (searchText != DEFAULT_SEARCH_TEXT)
			csrDetail.append("[searchText=" + searchText + "]");
		if (status > DEFAULT_STATUS)
			csrDetail.append("[status=" + status + "]");
		return csrDetail.toString();
	}

	public String getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

}

package com.payable.ttt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "t_task_end")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ORMTaskEnd {

	@Id
//	@Column(name = "id")
//	@SerializedName("id")
//	@GeneratedValue(generator = "uuid")
//	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Expose
	private String id;

	@Column(name = "task_id", insertable = false, updatable = false)
	private String taskId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id")
	private ORMTask task;

	private int is_active;

	private Date system_time;

	private Date enterd_time;
	
	private String remarks;
	
	public String getremarks() {
		return remarks;
	}
	
	public void setremarks(String remarks) {
		this.remarks = remarks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIs_active() {
		return is_active;
	}

	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}

	public Date getSystem_time() {
		return system_time;
	}

	public void setSystem_time(Date system_time) {
		this.system_time = system_time;
	}

	public Date getEnterd_time() {
		return enterd_time;
	}

	public void setEnterd_time(Date enterd_time) {
		this.enterd_time = enterd_time;
	}


	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public ORMTask getTask() {
		return task;
	}

	public void setTask(ORMTask task) {
		this.task = task;
	}

}

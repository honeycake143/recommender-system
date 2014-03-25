package com.pccw.suggest.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "sug_ctl_batch_log")
public class ControlBatchLog implements Serializable{

	private Integer ctl_batch_log_id;
	
	private String file_name;
	
	private String status;
	
	private Date batch_start_datetime;
	
	private Date last_process_datetime;
	
	private Integer last_process_step;
	
	private String last_process_name;
	
	private String last_process_status;
	
	private Date create_datetime;
	
	private Date last_mod_datetime;
	
	private String last_mod_user;

	@Id
	@GeneratedValue
	@Column(name = "ctl_batch_log_id")
	public Integer getCtl_batch_log_id() {
		return ctl_batch_log_id;
	}

	@Column(name = "file_name")
	public String getFile_name() {
		return file_name;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	@Column(name = "batch_start_datetime")
	public Date getBatch_start_datetime() {
		return batch_start_datetime;
	}

	@Column(name = "last_process_datetime")
	public Date getLast_process_datetime() {
		return last_process_datetime;
	}

	@Column(name = "last_process_step")
	public Integer getLast_process_step() {
		return last_process_step;
	}

	@Column(name = "last_process_name")
	public String getLast_process_name() {
		return last_process_name;
	}

	@Column(name = "last_process_status")
	public String getLast_process_status() {
		return last_process_status;
	}

	@Column(name = "create_datetime")
	public Date getCreate_datetime() {
		return create_datetime;
	}

	@Column(name = "last_mod_datetime", nullable = true)
	public Date getLast_mod_datetime() {
		return last_mod_datetime;
	}

	@Column(name = "last_mod_user", nullable = true)
	public String getLast_mod_user() {
		return last_mod_user;
	}

	public void setCtl_batch_log_id(Integer ctl_batch_log_id) {
		this.ctl_batch_log_id = ctl_batch_log_id;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBatch_start_datetime(Date batch_start_datetime) {
		this.batch_start_datetime = batch_start_datetime;
	}

	public void setLast_process_datetime(Date last_process_datetime) {
		this.last_process_datetime = last_process_datetime;
	}

	public void setLast_process_step(Integer last_process_step) {
		this.last_process_step = last_process_step;
	}

	public void setLast_process_name(String last_process_name) {
		this.last_process_name = last_process_name;
	}

	public void setLast_process_status(String last_process_status) {
		this.last_process_status = last_process_status;
	}

	public void setCreate_datetime(Date create_datetime) {
		this.create_datetime = create_datetime;
	}

	public void setLast_mod_datetime(Date last_mod_datetime) {
		this.last_mod_datetime = last_mod_datetime;
	}

	public void setLast_mod_user(String last_mod_user) {
		this.last_mod_user = last_mod_user;
	}
	
	
}

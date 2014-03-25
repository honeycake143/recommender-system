package com.pccw.suggest.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table (name = "sug_ctl_process_log")
public class ControlProcessLog implements Serializable{
	
	private Integer id;
	
	private ControlBatchLog ctl_batch_log;
	
	private Integer process_step;
	
	private String process_name;
	
	private String process_status;
	
	private Date create_datetime;
	
	private Date last_mod_datetime;
	
	private String last_mod_user;

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
//	@Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name="ctl_batch_log_id")
	public ControlBatchLog getCtl_batch_log() {
		return ctl_batch_log;
	}

	@Column(name = "process_step")
	public Integer getProcess_step() {
		return process_step;
	}

	@Column(name = "process_name")
	public String getProcess_name() {
		return process_name;
	}

	@Column(name = "process_status")
	public String getProcess_status() {
		return process_status;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCtl_batch_log(ControlBatchLog ctl_batch_log) {
		this.ctl_batch_log = ctl_batch_log;
	}

	public void setProcess_step(Integer process_step) {
		this.process_step = process_step;
	}

	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}

	public void setProcess_status(String process_status) {
		this.process_status = process_status;
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

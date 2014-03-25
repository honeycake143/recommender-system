package com.pccw.suggest.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "sug_ctl_process_step")
public class ControlProcessStep implements Serializable{

	private Integer id;
	
	private Integer step;
	
	private String name;
	
	private String description;
	
	private Integer enable;
	
	private Date create_datetime;
	
	private Date last_mod_datetime;
	
	private String last_mod_user;

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	@Column(name = "step")
	public Integer getStep() {
		return step;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return enable;
	}

	@Column(name = "create_datetime")
	public Date getCreate_datetime() {
		return create_datetime;
	}

	@Column(name = "last_mod_datetime")
	public Date getLast_mod_datetime() {
		return last_mod_datetime;
	}

	@Column(name = "last_mod_user")
	public String getLast_mod_user() {
		return last_mod_user;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
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

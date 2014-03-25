package com.pccw.suggest.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name = "sug_conf_filter")
public class ConfigFilter implements Serializable{
	
	private Integer id;
	
	private String type;
	
	private Float max_sum;
	
	private Integer enable;
	
	private Date create_date;
	
	private Date last_modify_date;
	
	private String last_modify_user;

	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "max_sum")
	public Float getMax_sum() {
		return max_sum;
	}

	public void setMax_sum(Float max_sum) {
		this.max_sum = max_sum;
	}

	
	@Column(name = "enable")
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer i) {
		this.enable = i;
	}

	
	@Column(name = "create_datetime")
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	@Column(name = "last_mod_datetime", nullable = true)
	public Date getLast_modify_date() {
		return last_modify_date;
	}

	public void setLast_modify_date(Date last_modify_date) {
		this.last_modify_date = last_modify_date;
	}

	@Column(name = "last_mod_user", nullable = true)
	public String getLast_modify_user() {
		return last_modify_user;
	}

	public void setLast_modify_user(String last_modify_user) {
		this.last_modify_user = last_modify_user;
	}
	
	

}

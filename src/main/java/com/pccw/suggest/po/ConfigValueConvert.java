package com.pccw.suggest.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name = "sug_conf_value_conv")
public class ConfigValueConvert implements Serializable{
	
	private Integer id;
	
	private String type;
	
	private Float multiply_factor;
	
	private Float addition_factor;
	
	private Float min_value;
	
	private Float max_value;
	
	private Date create_date;
	
	private Date last_mod_date;
	
	private String last_mod_user;
	
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

	@Column(name = "multiply_factor")
	public Float getMultiply_factor() {
		return multiply_factor;
	}

	public void setMultiply_factor(Float multiply_factor) {
		this.multiply_factor = multiply_factor;
	}

	@Column(name = "addition_factor")
	public Float getAddition_factor() {
		return addition_factor;
	}

	public void setAddition_factor(Float addition_factor) {
		this.addition_factor = addition_factor;
	}

	
	@Column(name = "min_value")
	public Float getMin_value() {
		return min_value;
	}

	public void setMin_value(Float min_value) {
		this.min_value = min_value;
	}

	@Column(name = "max_value")
	public Float getMax_value() {
		return max_value;
	}

	public void setMax_value(Float max_value) {
		this.max_value = max_value;
	}

	@Column(name = "create_datetime")
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	@Column(name = "last_mod_datetime", nullable = true)
	public Date getLast_mod_date() {
		return last_mod_date;
	}

	public void setLast_mod_date(Date last_mod_date) {
		this.last_mod_date = last_mod_date;
	}

	@Column(name = "last_mod_user" , nullable = true)
	public String getLast_mod_user() {
		return last_mod_user;
	}

	public void setLast_mod_user(String last_mod_user) {
		this.last_mod_user = last_mod_user;
	}
	
	
	
}

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
@Table (name = "sug_data_model")
public class DataModel implements Serializable{

	private Integer id;
	
	private ControlBatchLog ctl_batch_log;
	
	private DataFull data_full;
	
	private Date record_datetime;
	
	private Date record_date;
	
	private IDMap user_id_map;
	
	private IDMap item_id_map;
	
	private Float value;
	
	private String status;
	
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

	@ManyToOne(fetch=FetchType.LAZY)
//	@Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name="ctl_batch_log_id")
	public ControlBatchLog getCtl_batch_log() {
		return ctl_batch_log;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "data_full_id")
	public DataFull getData_full() {
		return data_full;
	}

	@Column(name = "record_datetime")
	public Date getRecord_datetime() {
		return record_datetime;
	}

	@Column(name = "record_date")
	public Date getRecord_date() {
		return record_date;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public IDMap getUser_id_map() {
		return user_id_map;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id")
	public IDMap getItem_id_map() {
		return item_id_map;
	}

	@Column(name = "value")
	public Float getValue() {
		return value;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	@Column(name = "enable")
	public Integer getEnable() {
		return enable;
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

	public void setData_full(DataFull data_full) {
		this.data_full = data_full;
	}

	public void setRecord_datetime(Date record_datetime) {
		this.record_datetime = record_datetime;
	}

	public void setRecord_date(Date record_date) {
		this.record_date = record_date;
	}

	public void setUser_id_map(IDMap user_id_map) {
		this.user_id_map = user_id_map;
	}

	public void setItem_id_map(IDMap item_id_map) {
		this.item_id_map = item_id_map;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public void setStatus(String status) {
		this.status = status;
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

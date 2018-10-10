package com.orasoft.data.link.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * 
 * create table dl_mapped_fields(
id_field int primary key auto_increment,
id_mapping int,
fst_field_name varchar(255),
fst_value_type varchar(100),
fst_platform varchar(100),
snd_field_name varchar(255),
snd_value_type varchar(100),
snd_platform varchar(100),
reg_date timestamp,
last_updated timestamp,
active int
);
 * */

@Entity
@Table(name = "dl_mapped_fields")
public class Field implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_field")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_mapping")
	private Mapping mapping;
	
	@Column(name = "fst_field_name", length = 255)
	private String firstFieldName;
	
	@Column(name = "fst_value_type", length = 100)
	private String firstValueType;
	
	@Column(name = "fst_platform", length = 100)
	private String firstFieldPlatform;
	
	@Column(name = "snd_field_name", length = 255)
	private String secondtFieldName;
	
	@Column(name = "snd_value_type", length = 100)
	private String secondValueType;
	
	@Column(name = "snd_platform", length = 100)
	private String secondFieldPlatform;
	
	@Column(name="active")
	private Long active;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	private Date regDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Mapping getMapping() {
		return mapping;
	}



	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}



	public String getFirstFieldName() {
		return firstFieldName;
	}



	public void setFirstFieldName(String firstFieldName) {
		this.firstFieldName = firstFieldName;
	}



	public String getFirstValueType() {
		return firstValueType;
	}



	public void setFirstValueType(String firstValueType) {
		this.firstValueType = firstValueType;
	}



	public String getFirstFieldPlatform() {
		return firstFieldPlatform;
	}



	public void setFirstFieldPlatform(String firstFieldPlatform) {
		this.firstFieldPlatform = firstFieldPlatform;
	}



	public String getSecondtFieldName() {
		return secondtFieldName;
	}



	public void setSecondtFieldName(String secondtFieldName) {
		this.secondtFieldName = secondtFieldName;
	}



	public String getSecondValueType() {
		return secondValueType;
	}



	public void setSecondValueType(String secondValueType) {
		this.secondValueType = secondValueType;
	}



	public String getSecondFieldPlatform() {
		return secondFieldPlatform;
	}



	public void setSecondFieldPlatform(String secondFieldPlatform) {
		this.secondFieldPlatform = secondFieldPlatform;
	}



	public Long getActive() {
		return active;
	}



	public void setActive(Long active) {
		this.active = active;
	}



	public Date getRegDate() {
		return regDate;
	}



	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}



	public Date getLastUpdated() {
		return lastUpdated;
	}



	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}

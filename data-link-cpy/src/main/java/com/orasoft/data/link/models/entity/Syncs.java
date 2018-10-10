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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * create table dl_syncs(
id_sync int primary key auto_increment,
id_user int,
body longtext,
operation varchar(100),
platform varchar(100),
entity varchar(100),
url varchar(255),
reg_date timestamp,
last_updated timestamp,
active int
);
 * */

@Entity
@Table(name="dl_syncs")
public class Syncs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sync")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_user")
	private User user;
	
	private String body;
	
	private String operation;
	
	private String platform;
	
	private String entity;
	
	private String url;
	
	@Column(name = "active")
	private Long active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reg_date")
	private Date regDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated")
	private Date lastUpdated;
	
	@PrePersist
	public void prePersist() {
		this.active = (long) 1;
		this.regDate = new Date();
		this.lastUpdated = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		this.lastUpdated = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	
}

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
 * create table dl_webhooks_is(
id_webhook int primary key auto_increment,
id_mapping int,
id_conn int,
key_webhook varchar(255),
event_key varchar(255),
hook_url varchar(255),
status varchar(255),
reg_date timestamp,
last_updated timestamp,
active int
);
 * */

@Entity
@Table(name="dl_webhooks_is")
public class ISWebhook implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_webhook")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_mapping")
	private Mapping mapping;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_conn")
	private Connector connector;
	
	@Column(name="key_webhook")
	private String key;
	
	@Column(name="event_key",length=255)
	private String eventKey;
	
	@Column(name="hook_url",length=255)
	private String hookUrl;
	
	@Column(name="status",length=255)
	private String status;
	
	@Column(name="active")
	private Long active;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	private Date regDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;
	
	@PrePersist
	public void prePersist() {
		this.active =(long)1;
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

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getHookUrl() {
		return hookUrl;
	}

	public void setHookUrl(String hookUrl) {
		this.hookUrl = hookUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

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
 * create table dl_callback_cw(
id_callback int primary key auto_increment,
id_mapping int,
id_conn int,
id_cw int,
url varchar(250),
object_id int,
type varchar(250),
level varchar(250),
reg_date timestamp,
last_updated timestamp,
active int,
member_id int,@SuppressWarnings("serial")

info longtext
);
 * */

@Entity
@Table(name="dl_callback_cw")
public class CWCallback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_callback")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_mapping")
	private Mapping mapping;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_conn")
	private Connector connector;
	
	@Column(name="id_cw")
	private Long idCw;
	
	@Column(name="url",length=255)
	private String url;
	
	@Column(name="object_id")
	private Long objectId;
	
	@Column(name="type",length=255)
	private String type;
	
	@Column(name="level",length=255)
	private String level;
	
	@Column(name="active")
	private Long active;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	private Date regDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;
	
	@Column(name="member_id")
	private Long memberId;
	
	@Column(name="info")
	private String info;
	
	
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

	public Long getIdCw() {
		return idCw;
	}

	public void setIdCw(Long idCw) {
		this.idCw = idCw;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
	
	
	
}

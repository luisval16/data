package com.orasoft.data.link.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * 
 * create table dl_mapping(
id_mapping int primary key auto_increment,
id_conn_fst int,
id_conn_snd int,
type varchar(100),
reg_date timestamp,
last_updated timestamp,
active int
);
 * */

@Entity
@Table(name = "dl_mapping")
public class Mapping implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mapping")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conn_fst")
	private Connector firstConnector;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conn_snd")
	private Connector secondConnector;

	@Column(name = "type", length = 100)
	private String type;

	@Column(name = "active")
	private Long active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reg_date")
	private Date regDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated")
	private Date lastUpdated;

	@Column(name = "img", length = 200)
	private String img;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mapping")
	private List<ISWebhook> webhooks;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<CWCallback> getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(List<CWCallback> callbacks) {
		this.callbacks = callbacks;
	}

	public List<ISWebhook> getWebhooks() {
		return webhooks;
	}

	public void setWebhooks(List<ISWebhook> webhooks) {
		this.webhooks = webhooks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mapping")
	private List<Field> fields;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mapping")
	private List<CWCallback> callbacks;

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

	public Connector getFirstConnector() {
		return firstConnector;
	}

	public void setFirstConnector(Connector firstConnector) {
		this.firstConnector = firstConnector;
	}

	public Connector getSecondConnector() {
		return secondConnector;
	}

	public void setSecondConnector(Connector secondConnector) {
		this.secondConnector = secondConnector;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

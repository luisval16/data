package com.orasoft.data.link.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="dl_infusion_tokens")
public class InfusionToken implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(name="token_actual",length=255)
	private String tokenActual;
	
	@NotEmpty
	@Column(name="token_refresh",length=255)
	private String tokenRefresh;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@PrePersist
	public void prePersist() {
		this.fecha = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTokenActual() {
		return tokenActual;
	}
	public void setTokenActual(String tokenActual) {
		this.tokenActual = tokenActual;
	}
	public String getTokenRefresh() {
		return tokenRefresh;
	}
	public void setTokenRefresh(String tokenRefresh) {
		this.tokenRefresh = tokenRefresh;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}

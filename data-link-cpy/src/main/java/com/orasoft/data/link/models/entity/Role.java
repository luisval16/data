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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.orasoft.userroleaccess.annotations.JustLetters;

@Entity
@Table(name="dl_role")
public class Role implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_role")
	private Long id;
	
	@NotEmpty
	@JustLetters
	@Column(name="rol_name",length=30)
	private String roleName;
	
	@Column(name="active")
	private Long active;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	private Date regDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

	@Transient
	private boolean isActive;
	
	@OneToMany(mappedBy="role",fetch=FetchType.LAZY)
	private List<UserXRole> users;
	
	@OneToMany(mappedBy="module",fetch=FetchType.LAZY)
	private List<RoleXModule> modules;
	
	
	@PrePersist
	public void prePersist() {
		this.active = (long)1;
		this.regDate = new Date();
		this.lastUpdated =  new Date();
		
	}
	
	@PreUpdate
	public void preUpdate() {
		this.lastUpdated =  new Date();
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
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



	public boolean isActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}



	public List<UserXRole> getUsers() {
		return users;
	}



	public void setUsers(List<UserXRole> users) {
		this.users = users;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
}

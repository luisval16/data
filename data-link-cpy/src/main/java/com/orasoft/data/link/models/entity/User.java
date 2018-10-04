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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.orasoft.userroleaccess.annotations.ContactNumberConstraint;
import com.orasoft.userroleaccess.annotations.FieldsValueMatch;
import com.orasoft.userroleaccess.annotations.UniqueEmail;

@Entity
@Table(name = "dl_user")
/*
 * @FieldsValueMatch.List({
 * 
 * @FieldsValueMatch( field = "password", fieldMatch = "repassword", message=
 * "Password do not match!" ) })
 */
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;

	@NotEmpty
	@Column(name = "first_name", length = 30)
	private String firstName;

	@NotEmpty
	@Column(name = "last_name", length = 30)
	private String lastName;

	@Email
	@NotEmpty
	// @UniqueEmail
	@Column(name = "email", length = 50, unique = true)
	private String email;

	@NotEmpty
	@Column(name = "user_pass", length = 255)
	private String password;

	@NotEmpty
	@Column(name = "company", length = 50)
	private String company;

	
	@Column(name = "phone_number", length = 20)
	private String phoneNumber;

	@Column(name = "active")
	private Long active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reg_date")
	private Date regDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated")
	private Date lastUpdated;

	@Transient
	private boolean isEnabled;

	@Transient
	private String repassword;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserXRole> roles;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Connector> connectors;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<InfusionSoftToken> infusionSoftTokens;

	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<ConnectWiseCredentials> connectWiseCredentials;

	
	public Long getId() {
		return id;
	}

	@PrePersist
	public void prePersist() {
		this.regDate = new Date();
		this.lastUpdated = new Date();
		this.active = (long) 1;
	}

	@PreUpdate
	public void preUpdate() {
		this.lastUpdated = new Date();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public List<UserXRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserXRole> roles) {
		this.roles = roles;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

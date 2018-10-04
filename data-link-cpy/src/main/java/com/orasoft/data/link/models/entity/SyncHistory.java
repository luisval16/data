package com.orasoft.data.link.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
@Table(name = "dl_all_syncs")
public class SyncHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String body;

	@Column(length = 50)
	private String tipo;

	private String entidad;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Transient
	private JSONObject dataObject;
	
	@Transient
	private String aplicacion;
	
	@Transient
	private String tabla;

	public SyncHistory() {

	}

	public SyncHistory(Long id, String body, String tipo, String entidad, Date fecha) {

		this.id = id;
		this.body = body;
		this.tipo = tipo;
		this.entidad = entidad;
		this.fecha = fecha;
		try {
			this.dataObject = new JSONObject(body);
		} catch (JSONException e) {
			// TODO: handle exception
			System.err.println("Constructor SyncHistory Object");
			e.printStackTrace();
		}
		if (entidad.contains("infusionsoft")) {
			this.aplicacion = "InfusionSoft";
		}else if(entidad.contains("connectwise")){
			this.aplicacion = "ConnectWise";
		}else {
			this.aplicacion = "fail";
		}
		
		if (entidad.contains("companies")) {
			this.tabla = "Company";
		}else if(entidad.contains("contacts")) {
			this.tabla = "Contact";
		}else if(entidad.contains("opportunities")) {
			this.tabla = "Opportunity";
		}else {
			this.tabla = "fail";
		}
		
	}

	public Long getId() {
		return id;
	}
	
	public void setAppAndTabbla() {
		if (entidad.contains("infusionsoft")) {
			this.aplicacion = "InfusionSoft";
		}else if(entidad.contains("connectwise")){
			this.aplicacion = "ConnectWise";
		}else {
			this.aplicacion = "fail";
		}
		
		if (entidad.contains("companies")) {
			this.tabla = "Company";
		}else if(entidad.contains("contacts")) {
			this.tabla = "Contact";
		}else if(entidad.contains("opportunities")) {
			this.tabla = "Opportunity";
		}else {
			this.tabla = "fail";
		}
		
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public JSONObject getDataObject() {
		return dataObject;
	}

	public void setDataObject(String dataObject) {
		try {
			this.dataObject = new JSONObject(dataObject);
		} catch (JSONException e) {
			System.err.println("Setter dataObject");
			e.printStackTrace();
		}
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	
	

}

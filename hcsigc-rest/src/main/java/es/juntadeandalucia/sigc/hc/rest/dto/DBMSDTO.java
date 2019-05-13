package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.DBMSBO;

public class DBMSDTO extends GenericDTO<DBMSBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("connectionRegex")
	private String connectionRegex;

	public DBMSDTO() {
		super();
	}

	public DBMSDTO(DBMSBO dbmsBO) {
		super(dbmsBO);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConnectionRegex() {
		return connectionRegex;
	}

	public void setConnectionRegex(String connectionRegex) {
		this.connectionRegex = connectionRegex;
	}

	public DBMSBO toBO(long id) {
		DBMSBO dbmsBO = new DBMSBO();
		dbmsBO.setId(id);
		dbmsBO.setName(this.getName());
		dbmsBO.setConnectionRegex(this.getConnectionRegex());
		return dbmsBO;
	}

	public DBMSBO toBO() {
		DBMSBO dbmsBO = new DBMSBO();
		dbmsBO.setName(this.getName());
		dbmsBO.setConnectionRegex(this.getConnectionRegex());
		return dbmsBO;
	}

	@Override
	protected void initalizeFromBO(DBMSBO dbmsBO) {
		this.id = dbmsBO.getId();
		this.name = dbmsBO.getName();
		this.connectionRegex = dbmsBO.getConnectionRegex();
	}
}

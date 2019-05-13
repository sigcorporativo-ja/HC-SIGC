package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.DBConnectionBO;

public class DBConnectionCreationDTO extends DBConnectionDTO {

	@JsonProperty("password")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DBConnectionCreationDTO() {
		super();

	}

	public DBConnectionCreationDTO(Long id) {
		super(id);
	}

	public DBConnectionCreationDTO(DBConnectionBO dbConnectionBO) {
		super(dbConnectionBO);
		this.password = dbConnectionBO.getPassword();
	}
	
	public DBConnectionBO toBO() {
		DBConnectionBO dbConnectionBO = super.toBO();
		if (this.getId() != null) {
			dbConnectionBO.setId((Long) this.getId());
		}
		dbConnectionBO.setPassword(this.getPassword());
		return dbConnectionBO;
	}
	
	public DBConnectionBO toBO(long id) {
		DBConnectionBO dbConnectionBO = toBO();
		
		dbConnectionBO.setId((Long) id);
		return dbConnectionBO;
	}

}

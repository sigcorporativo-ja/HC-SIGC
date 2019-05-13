package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.DBConnectionBO;

public class DBConnectionDTO extends GenericDTO<DBConnectionBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("host")
	private String host;

	@JsonProperty("port")
	private int port;

	@JsonProperty("database")
	private String database;

	@JsonProperty("schema")
	private String schema;

	@JsonProperty("user")
	private String user;

	@JsonProperty("dbms")
	private DBMSDTO dbms;

	public DBConnectionDTO() {
		super();
	}
	
	public DBConnectionDTO(Long id) {
		super();
		this.id = id;
	}

	public DBConnectionDTO(DBConnectionBO dbConnectionBO) {
		super(dbConnectionBO);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public DBMSDTO getDbms() {
		return dbms;
	}

	public void setDbms(DBMSDTO dbms) {
		this.dbms = dbms;
	}

	public DBConnectionBO toBO(long id) {
		DBConnectionBO dbConnectionBO = new DBConnectionBO();
		dbConnectionBO.setId(id);
		dbConnectionBO.setName(this.getName());
		dbConnectionBO.setHost(this.getHost());
		dbConnectionBO.setPort(this.getPort());
		dbConnectionBO.setDatabase(this.getDatabase());
		dbConnectionBO.setSchema(this.getSchema());
		dbConnectionBO.setUser(this.getUser());
		return dbConnectionBO;
	}

	public DBConnectionBO toBO() {
		DBConnectionBO dbConnectionBO = new DBConnectionBO();
		dbConnectionBO.setName(this.getName());
		dbConnectionBO.setHost(this.getHost());
		dbConnectionBO.setPort(this.getPort());
		dbConnectionBO.setDatabase(this.getDatabase());
		dbConnectionBO.setSchema(this.getSchema());
		dbConnectionBO.setUser(this.getUser());
		return dbConnectionBO;
	}

	@Override
	protected void initalizeFromBO(DBConnectionBO dbConnectionBO) {
		this.id = dbConnectionBO.getId();
		this.name = dbConnectionBO.getName();
		this.host = dbConnectionBO.getHost();
		this.port = dbConnectionBO.getPort();
		this.database = dbConnectionBO.getDatabase();
		this.schema = dbConnectionBO.getSchema();
		this.user = dbConnectionBO.getUser();
		if (dbConnectionBO.getDbms() != null)
			this.dbms = new DBMSDTO(dbConnectionBO.getDbms());
	}

}

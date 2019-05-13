package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.DBConnection;

public class DBConnectionBO extends GenericBO<DBConnection> {

	private static final long serialVersionUID = -9038225163239376878L;

	private String name;
	private String host;
	private int port;
	private String database;
	private String schema;
	private String user;
	private String password;
	private List<DatasetBO> datasets = new ArrayList<>();
	private DBMSBO dbms;
	private List<GroupBO> groups = new ArrayList<>();

	private DBConnection dbConnection;

	public DBConnectionBO() {
		super();
	}

	public DBConnectionBO(DBConnection dbConnection) {
		this(dbConnection, false);
	}

	public DBConnectionBO(DBConnection dbConnection, boolean eager) {
		super(dbConnection, eager);
	}

	@Override
	protected void initializeFromEntity(DBConnection entity, boolean eager) {
		if (entity != null) {
			this.setDbConnection(entity);
			this.setId((Long) entity.getId());
			this.name = entity.getName();
			this.host = entity.getHost();
			this.port = entity.getPort();
			this.database = entity.getDatabase();
			this.schema = entity.getSchema();
			this.user = entity.getUser();
			this.password = entity.getPassword();
			this.dbms = new DBMSBO(entity.getDbms());
			if (eager) {
				this.datasets = entity.getDatasets().stream().map(DatasetBO::new).collect(Collectors.toList());
				this.groups = entity.getGroups().stream().map(GroupBO::new).collect(Collectors.toList());
			}
		}
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<DatasetBO> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<DatasetBO> datasets) {
		this.datasets = datasets;
	}

	public DBMSBO getDbms() {
		return dbms;
	}

	public void setDbms(DBMSBO dbms) {
		this.dbms = dbms;
	}

	public List<GroupBO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupBO> groups) {
		this.groups = groups;
	}

	public DBConnection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public DBConnection toEntity() {
		DBConnection dbconnection = new DBConnection();
		dbconnection.setId(this.getId());
		dbconnection.setName(this.name);
		dbconnection.setHost(this.getHost());
		dbconnection.setPort(this.getPort());
		dbconnection.setDatabase(this.getDatabase());
		dbconnection.setSchema(this.getSchema());
		dbconnection.setUser(this.getUser());
		dbconnection.setPassword(this.getPassword());
		dbconnection.setVersion(this.getVersion());
		return dbconnection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				DBConnectionBO other = (DBConnectionBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}
}

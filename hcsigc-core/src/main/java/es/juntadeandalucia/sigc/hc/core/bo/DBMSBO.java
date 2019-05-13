package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.DBMS;

public class DBMSBO extends GenericBO<DBMS> {

	private static final long serialVersionUID = -7753332937677523215L;

	private String name;
	private String connectionRegex;
	private List<DBConnectionBO> dbConnections = new ArrayList<>();

	public DBMSBO() {
		super();
	}

	public DBMSBO(DBMS dbms) {
		this(dbms, false);
	}

	public DBMSBO(DBMS dbms, boolean eager) {
		super(dbms, eager);
	}

	@Override
	protected void initializeFromEntity(DBMS entity, boolean eager) {
		if (entity != null) {
			this.setId((Long) entity.getId());
			this.name = entity.getName();
			this.connectionRegex = entity.getConnectionRegex();
			if (eager) {
				this.dbConnections = entity.getDbConnections().stream().map(DBConnectionBO::new)
						.collect(Collectors.toList());
			}
		}
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

	public List<DBConnectionBO> getDbConnections() {
		return dbConnections;
	}

	public void setDbConnections(List<DBConnectionBO> dbConnections) {
		this.dbConnections = dbConnections;
	}

	public DBMS toEntity() {
		DBMS dbms = new DBMS();
		dbms.setId(this.getId());
		dbms.setName(this.getName());
		dbms.setConnectionRegex(this.getConnectionRegex());
		return dbms;
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
				DBMSBO other = (DBMSBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}

}

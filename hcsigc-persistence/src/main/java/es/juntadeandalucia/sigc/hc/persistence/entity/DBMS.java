package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "dbms")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DBMS extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = 7314016785848240421L;

	@Id
	@GeneratedValue(generator = "seq_dbs", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_dbs", sequenceName = "seq_x_dbs", allocationSize = 1)
	@Column(name = "x_dbs")
	private Long id;

	@NotNull
	@Length(max = 150, message = "{dbconn.name.max}")
	@Column(name = "t_name")
	private String name;

	@NotNull
	@Length(max = 150, message = "{dbconn.connection_regex.max}")
	@Column(name = "t_connection_regex")
	private String connectionRegex;

	@OneToMany(mappedBy = "dbms")
	private List<DBConnection> dbConnections = new ArrayList<>();

	public DBMS() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<DBConnection> getDbConnections() {
		return dbConnections;
	}

	public void setDbConnections(List<DBConnection> dbConnections) {
		this.dbConnections = dbConnections;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				DBMS other = (DBMS) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

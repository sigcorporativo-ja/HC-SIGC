package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;
import org.jasypt.hibernate4.type.EncryptedStringType;

@TypeDef(
		name="encryptedString",
	    typeClass=EncryptedStringType.class,
	    parameters= {
	        @Parameter(name="encryptorRegisteredName", value="defaultStringEncryptor")
	    }
)
@Entity
@Table(name = "db_connections")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DBConnection extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -3138540079359473873L;

	@Id
	@GeneratedValue(generator = "seq_dbconn", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_dbconn", sequenceName = "seq_x_dbconn", allocationSize = 1)
	@Column(name = "x_dbconn")
	private Long id;

	@NotNull
	@Length(max = 150, message = "{dbconn.name.max}")
	@Column(name = "t_name", unique=true)
	private String name;

	@NotNull
	@Length(max = 150, message = "{dbconn.host.max}")
	@Column(name = "t_host")
	private String host;

	@NotNull
	@Column(name = "n_port")
	private int port;

	@NotNull
	@Length(max = 150, message = "{dbconn.database.max}")
	@Column(name = "t_database")
	private String database;

	@NotNull
	@Length(max = 150, message = "{dbconn.schema.max}")
	@Column(name = "t_schema")
	private String schema;

	@NotNull
	@Length(max = 150, message = "{dbconn.user.max}")
	@Column(name = "t_user")
	private String user;

	@Type(type = "encryptedString")
	@NotNull
	@Length(max = 150, message = "{dbconn.password.max}")
	@Column(name = "t_password")
	private String password;

	@OneToMany(mappedBy = "dbConnection")
	private List<Dataset> datasets = new ArrayList<>();

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey = @ForeignKey(name = "dbs_x_dbs"))
	private DBMS dbms;

	@ManyToMany(mappedBy = "dbconnections")
	private List<Group> groups = new ArrayList<>();

	public DBConnection() {
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

	public List<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}

	public DBMS getDbms() {
		return dbms;
	}

	public void setDbms(DBMS dbms) {
		this.dbms = dbms;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
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
				DBConnection other = (DBConnection) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

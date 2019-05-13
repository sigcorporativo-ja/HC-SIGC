package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.ForeignKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "datasets")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dataset extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -6580863086832693971L;

	@Id
	@GeneratedValue(generator = "seq_dataset", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_dataset", sequenceName = "seq_x_dataset", allocationSize = 1)
	@Column(name = "x_data")
	private Long id;

	@Length(max = 150, message = "{dataset.tableName.max}")
	@Column(name = "t_table_name")
	private String tableName;

	@NotNull
	@Length(max = 150, message = "{dataset.name.max}")
	@Column(name = "t_name")
	private String name;

	@Length(max = 500, message = "{dataset.description.max}")
	@Column(name = "t_description")
	private String description;

	@Length(max = 250, message = "{dataset.uuidMetadata.max}")
	@Column(name = "t_uuid_metadata")
	private String uuidMetadata;

	@Column(name = "n_size")
	private Long size;

	@Column(name = "l_global")
	private boolean global;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey = @ForeignKey(name = "user_x_user"))
	private User user;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "reco_x_reco"))
	private RemoteConnection remoteConnection;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "dbco_x_dbco"))
	private DBConnection dbConnection;

	@NotNull
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "daty_x_daty"))
	private DataType dataType;

	@ManyToMany(mappedBy = "datasets")
	private List<GroupUser> groupsUsers = new ArrayList<>();
	
	@ManyToMany(mappedBy = "favorites")
	private List<User> usersFavorites = new ArrayList<>();

	public Dataset() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUuidMetadata() {
		return uuidMetadata;
	}

	public void setUuidMetadata(String uuidMetadata) {
		this.uuidMetadata = uuidMetadata;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public RemoteConnection getRemoteConnection() {
		return remoteConnection;
	}

	public void setRemoteConnection(RemoteConnection remoteConnection) {
		this.remoteConnection = remoteConnection;
	}

	public DBConnection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public List<GroupUser> getGroupsUsers() {
		return groupsUsers;
	}

	public void setGroupsUsers(List<GroupUser> groupsUsers) {
		this.groupsUsers = groupsUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				Dataset other = (Dataset) obj;
				if (!id.equals(other.id)) {
					result = false;
				}
			}
		}
		return result;
	}

}

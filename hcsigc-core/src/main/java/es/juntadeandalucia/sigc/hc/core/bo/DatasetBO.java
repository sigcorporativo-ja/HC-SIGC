package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.Dataset;

public class DatasetBO extends GenericBO<Dataset> {

	private static final long serialVersionUID = -8700183847579139321L;

	private String name;

	private String tableName;

	private String created;

	private Date creationDate;

	private String modified;

	private Date modificationDate;

	private Long version;

	private String description;

	private boolean global;

	private Long size;

	private String uuidMetadata;

	private DBConnectionBO dbConnection;

	private DataTypeBO dataType;

	private RemoteConnectionBO remoteConnection;

	private UserBO user;
	
	private Set<GroupBO> groups;
	
	private Set<UserBO> shareToUsers;
	
	public DatasetBO() {
		super();
	}

	public DatasetBO(Dataset dataset) {
		this(dataset, false);
	}

	public DatasetBO(Dataset dataset, boolean eager) {
		super(dataset, eager);
	}

	@Override
	protected void initializeFromEntity(Dataset dataset, boolean eager) {
		this.setId((Long) dataset.getId());
		this.name = dataset.getName();
		this.created = dataset.getCreated();
		this.creationDate = dataset.getCreationDate();
		this.description = dataset.getDescription();
		this.global = dataset.isGlobal();
		this.modified = dataset.getModified();
		this.modificationDate = dataset.getModificationDate();
		this.size = dataset.getSize();
		this.tableName = dataset.getTableName();
		this.uuidMetadata = this.getUuidMetadata();
		this.user = new UserBO(dataset.getUser());
		this.dataType = new DataTypeBO(dataset.getDataType());
		if (dataset.getRemoteConnection() != null)
			this.remoteConnection = new RemoteConnectionBO(dataset.getRemoteConnection());
		if (dataset.getDbConnection() != null)
			this.dbConnection = new DBConnectionBO(dataset.getDbConnection());
		if (eager) {
			if(dataset.getGroupsUsers() != null)
				this.groups = dataset.getGroupsUsers().stream().map(groupUser -> new GroupBO(groupUser.getGroup()))
						.collect(Collectors.toSet());
		}
		
		//This is only used to show the shareGroup icon in paginate
		if(dataset.getGroupsUsers() != null)
			this.shareToUsers = dataset.getGroupsUsers().stream().map(groupUser -> new UserBO(groupUser.getUser()))
					.collect(Collectors.toSet());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getUuidMetadata() {
		return uuidMetadata;
	}

	public void setUuidMetadata(String uuidMetadata) {
		this.uuidMetadata = uuidMetadata;
	}

	public DBConnectionBO getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DBConnectionBO dbConnection) {
		this.dbConnection = dbConnection;
	}

	public DataTypeBO getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeBO dataType) {
		this.dataType = dataType;
	}

	public RemoteConnectionBO getRemoteConnection() {
		return remoteConnection;
	}

	public void setRemoteConnection(RemoteConnectionBO remoteConnection) {
		this.remoteConnection = remoteConnection;
	}

	public UserBO getUser() {
		return user;
	}

	public void setUser(UserBO user) {
		this.user = user;
	}

	public Set<GroupBO> getGroups() {
		return groups;
	}

	public void setGroups(Set<GroupBO> groups) {
		this.groups = groups;
	}
	
	public Set<UserBO> getShareToUsers() {
		return shareToUsers;
	}

	public void setShareToUsers(Set<UserBO> shareToUsers) {
		this.shareToUsers = shareToUsers;
	}

	public Dataset toEntity() {
		Dataset dataset = new Dataset();
		dataset.setId(this.getId());
		dataset.setName(this.getName());
		dataset.setCreated(this.getCreated());
		dataset.setCreationDate(this.getCreationDate());
		dataset.setDescription(this.getDescription());
		dataset.setGlobal(this.isGlobal());
		dataset.setModified(this.getModified());
		dataset.setModificationDate(this.getModificationDate());
		dataset.setSize(this.getSize());
		dataset.setUuidMetadata(this.getUuidMetadata());
		dataset.setTableName(this.getTableName());
		dataset.setDataType(this.getDataType().toEntity());
		dataset.setUser(this.getUser().toEntity());
		return dataset;
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
				DatasetBO other = (DatasetBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}
}

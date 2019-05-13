package es.juntadeandalucia.sigc.hc.rest.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.DatasetBO;

public class DatasetDTO extends GenericDTO<DatasetBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("tableName")
	private String tableName;

	@JsonProperty("description")
	private String description;

	@JsonProperty("global")
	private boolean global;

	@JsonProperty("size")
	private Long size;

	@JsonProperty("uuidMetadata")
	private String uuidMetadata;

	@JsonProperty("dbConnection")
	private DBConnectionDTO dbConnection;
	
	@JsonProperty("dataType")
	private DataTypeDTO dataType;

	@JsonProperty("remoteConnection")
	private RemoteConnectionDTO remoteConnection;

	@JsonProperty("user")
	private UserDTO user;
	
	@JsonProperty("groups")
	private Set<GroupDTO> groups;
	
	@JsonProperty("shareToUsers")
	private Set<UserDTO> shareToUsers;

	public DatasetDTO() {
		super();
	}
	
	public DatasetDTO(DatasetBO datasetBO) {
		super(datasetBO);
	}

	public DBConnectionDTO getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DBConnectionDTO dbConnection) {
		this.dbConnection = dbConnection;
	}

	public DataTypeDTO getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeDTO dataType) {
		this.dataType = dataType;
	}
	
	public RemoteConnectionDTO getRemoteConnection() {
		return remoteConnection;
	}

	public void setRemoteConnection(RemoteConnectionDTO remoteConnection) {
		this.remoteConnection = remoteConnection;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
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
	
	public Set<GroupDTO> getGroups() {
		return groups;
	}

	public void setGroups(Set<GroupDTO> groups) {
		this.groups = groups;
	}

	public Set<UserDTO> getShareToUsers() {
		return shareToUsers;
	}

	public void setShareToUsers(Set<UserDTO> shareToUsers) {
		this.shareToUsers = shareToUsers;
	}
	
	public DatasetBO toBO(long id) {
		DatasetBO datasetBO = new DatasetBO();
		datasetBO.setId(id);
		datasetBO.setDescription(this.getDescription());
		datasetBO.setName(this.getName());
		datasetBO.setTableName(this.getTableName());
		datasetBO.setSize(this.getSize());
		datasetBO.setGlobal(this.isGlobal());
		datasetBO.setUuidMetadata(this.getUuidMetadata());
		
		//Necessary because this propert only apear in DTO
		datasetBO.setGroups(this.groups.stream().map(group -> group.toBO(group.getId())).collect(Collectors.toSet()));
		return datasetBO;
	}
	
	public DatasetBO toBO() {
		DatasetBO datasetBO = new DatasetBO();
		datasetBO.setDescription(this.getDescription());
		datasetBO.setName(this.getName());
		datasetBO.setTableName(this.getTableName());
		datasetBO.setSize(this.getSize());
		datasetBO.setGlobal(this.isGlobal());
		datasetBO.setUuidMetadata(this.getUuidMetadata());
		return datasetBO;
	}

	@Override
	protected void initalizeFromBO(DatasetBO datasetBO) {
		this.id = datasetBO.getId();
		this.name = datasetBO.getName();
		this.tableName = datasetBO.getTableName();
		this.description = datasetBO.getDescription();
		this.global = datasetBO.isGlobal();
		this.size = datasetBO.getSize();
		if(datasetBO.getDataType() != null)
			this.dataType = new DataTypeDTO(datasetBO.getDataType());
		if(datasetBO.getUser() != null)
			this.user = new UserDTO(datasetBO.getUser());
		if(datasetBO.getGroups() != null)
			this.groups = datasetBO.getGroups().stream().map(group -> new GroupDTO(group)).collect(Collectors.toSet());
		if(datasetBO.getShareToUsers() != null)
			this.shareToUsers = datasetBO.getShareToUsers().stream().map(user -> new UserDTO(user)).collect(Collectors.toSet());
	}
}

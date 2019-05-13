package es.juntadeandalucia.sigc.hc.rest.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.PermissionBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;

public class UserDTO {

	/**
	 * Id of the DTO
	 */
	@JsonProperty("id")
	protected Long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("surname")
	private String surname;

	@JsonProperty("email")
	private String mail;

	@JsonProperty("username")
	private String username;

	@JsonProperty("ldap")
	private boolean ldap;

	@JsonProperty("quota")
	private Long quota;

	@JsonProperty("usedQuota")
	private Long usedQuota;

	@JsonProperty("permissions")
	private List<PermissionDTO> permissions;

	@JsonProperty("org_unit")
	private OrganizationUnitDTO organizationUnit;

	public UserDTO() {
		super();
	}

	/**
	 * Generic constructor for a DTO
	 * 
	 * @param id
	 */
	public UserDTO(Long id) {
		super();
		this.id = id;
	}

	public UserDTO(UserBO userBO) {
		this.initalizeFromBO(userBO);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isLdap() {
		return ldap;
	}

	public void setLdap(boolean ldap) {
		this.ldap = ldap;
	}

	public Long getQuota() {
		return quota;
	}

	public void setQuota(Long quota) {
		this.quota = quota;
	}

	public Long getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Long usedQuota) {
		this.usedQuota = usedQuota;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	public OrganizationUnitDTO getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(OrganizationUnitDTO organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public UserBO toBO(long id) {
		UserBO userBO = toBO();
		userBO.setId(String.valueOf(id));
		return userBO;
	}

	public UserBO toBO() {
		UserBO userBO = new UserBO();
		if (this.getId() != null) {
			userBO.setId(String.valueOf(this.getId()));
		}
		userBO.setName(this.getName());
		userBO.setSurname(this.getSurname());
		userBO.setMail(this.getMail());
		userBO.setUsername(this.getUsername());
		userBO.setQuota(this.getQuota());
		userBO.setUsedQuota(this.getUsedQuota());
		if (this.getPermissions() != null) {
			userBO.setPermissions(
					this.getPermissions().stream().map(p -> p.toBO(p.getId())).collect(Collectors.toList()));
		}
		return userBO;
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
		boolean equals = false;
		if (getId() != null && obj != null && obj instanceof UserDTO) {
			UserDTO userDTO = (UserDTO) obj;
			equals = getId().equals(userDTO.getId());
		}
		return equals;
	}

	private void initalizeFromBO(UserBO userBO) {
		this.id = Long.parseLong(userBO.getId());
		this.name = userBO.getName();
		this.surname = userBO.getSurname();
		this.mail = userBO.getMail();
		this.username = userBO.getUsername();
		this.quota = userBO.getQuota();
		this.usedQuota = userBO.getUsedQuota();
		List<PermissionBO> permissionsBO = userBO.getPermissions();
		if (permissionsBO != null) {
			this.permissions = permissionsBO.stream().map(PermissionDTO::new).collect(Collectors.toList());
		}
		if (userBO.getOrganizationUnit() != null)
			this.organizationUnit = new OrganizationUnitDTO(userBO.getOrganizationUnit());
	}
}

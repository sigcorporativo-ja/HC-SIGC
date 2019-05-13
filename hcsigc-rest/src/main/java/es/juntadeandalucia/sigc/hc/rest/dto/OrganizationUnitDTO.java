package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.OrganizationUnitBO;

public class OrganizationUnitDTO extends GenericDTO<OrganizationUnitBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	public OrganizationUnitDTO() {
		super();
	}
	
	public OrganizationUnitDTO(OrganizationUnitBO organizationUnitBO) {
		super(organizationUnitBO);
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

	public OrganizationUnitBO toBO(long id) {
		OrganizationUnitBO ouBO = new OrganizationUnitBO();
		ouBO.setId(id);
		ouBO.setDescription(this.getDescription());
		ouBO.setName(this.getName());
		return ouBO;
	}
	
	public OrganizationUnitBO toBO() {
		OrganizationUnitBO ouBO = new OrganizationUnitBO();
		ouBO.setDescription(this.getDescription());
		ouBO.setName(this.getName());		
		return ouBO;
	}

	@Override
	protected void initalizeFromBO(OrganizationUnitBO organizationUnitBO) {
		this.id = organizationUnitBO.getId();
		this.name = organizationUnitBO.getName();
		this.description = organizationUnitBO.getDescription();
	}
}

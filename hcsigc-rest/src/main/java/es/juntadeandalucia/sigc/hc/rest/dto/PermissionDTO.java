package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.PermissionBO;

public class PermissionDTO extends GenericDTO<PermissionBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("code")
	private String code;

	public PermissionDTO() {
		super();
	}

	public PermissionDTO(PermissionBO bo) {
		super(bo);
	}

	@Override
	protected void initalizeFromBO(PermissionBO bo) {
		this.id = bo.getId();
		this.name = bo.getName();
		this.code = bo.getCode();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public PermissionBO toBO() {
		PermissionBO permissionBO = new PermissionBO();
		permissionBO.setCode(this.getCode());
		permissionBO.setName(this.getName());

		return permissionBO;
	}

	public PermissionBO toBO(Long id) {
		PermissionBO permissionBO = this.toBO();
		permissionBO.setId(id);

		return permissionBO;
	}

}

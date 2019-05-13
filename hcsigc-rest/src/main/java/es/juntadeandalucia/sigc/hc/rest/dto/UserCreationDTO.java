package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.UserBO;

public class UserCreationDTO extends UserDTO {

	@JsonProperty("password")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserCreationDTO() {
		super();

	}

	public UserCreationDTO(Long id) {
		super(id);
	}

	public UserCreationDTO(UserBO userBO) {
		super(userBO);
		this.password = userBO.getPassword();
	}
	
	public UserBO toBO() {
		UserBO userBo = super.toBO();
		if (this.getId() != null) {
			userBo.setId(String.valueOf(this.getId()));
		}
		userBo.setPassword(this.getPassword());
		return userBo;
	}
	
	public UserBO toBO(long id) {
		UserBO userBO = toBO();
		
		userBO.setId(String.valueOf(id));
		return userBO;
	}

}

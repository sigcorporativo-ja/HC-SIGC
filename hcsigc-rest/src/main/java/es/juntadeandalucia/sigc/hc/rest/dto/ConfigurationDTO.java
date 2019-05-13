package es.juntadeandalucia.sigc.hc.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.ConfigurationBO;

public class ConfigurationDTO extends GenericDTO<ConfigurationBO> {

	@JsonProperty("key")
	private String key;

	@JsonProperty("value")
	private String value;

	@JsonProperty("description")
	private String description;

	public ConfigurationDTO() {
		super();
	}

	public ConfigurationDTO(ConfigurationBO configurationBO) {
		super(configurationBO);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ConfigurationBO toBO(long id) {
		ConfigurationBO configBO = new ConfigurationBO();
		configBO.setId(id);
		configBO.setKey(this.getKey());
		configBO.setValue(this.getValue());
		configBO.setDescription(this.getDescription());
		return configBO;
	}

	public ConfigurationBO toBO() {
		ConfigurationBO configBO = new ConfigurationBO();
		configBO.setKey(this.getKey());
		configBO.setValue(this.getValue());
		configBO.setDescription(this.getDescription());
		return configBO;
	}

	@Override
	protected void initalizeFromBO(ConfigurationBO configurationBO) {
		this.id = configurationBO.getId();
		this.key = configurationBO.getKey();
		this.value = configurationBO.getValue();
		this.description = configurationBO.getDescription();
	}
}
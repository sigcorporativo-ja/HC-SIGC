package es.juntadeandalucia.sigc.hc.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.DataTypeBO;

public class DataTypeDTO extends GenericDTO<DataTypeBO> {

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;
	
	@JsonProperty("datasets")
	private List<DatasetDTO> datasets;
	
	public DataTypeDTO() {
		super();
	}

	public DataTypeDTO(DataTypeBO dataTypeBO) {
		super(dataTypeBO);
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
	
	public List<DatasetDTO> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<DatasetDTO> datasets) {
		this.datasets = datasets;
	}

	public DataTypeBO toBO(long id) {
		DataTypeBO dataTypeBO = new DataTypeBO();
		dataTypeBO.setId(id);
		dataTypeBO.setName(this.getName());
		dataTypeBO.setDescription(this.getDescription());
		return dataTypeBO;
	}

	public DataTypeBO toBO() {
		DataTypeBO dataTypeBO = new DataTypeBO();
		dataTypeBO.setName(this.getName());
		dataTypeBO.setDescription(this.getDescription());
		return dataTypeBO;
	}

	@Override
	protected void initalizeFromBO(DataTypeBO dataTypeBO) {
		this.id = dataTypeBO.getId();
		this.name = dataTypeBO.getName();
		this.description = dataTypeBO.getDescription();
	}
}

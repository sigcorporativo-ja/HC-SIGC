package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.juntadeandalucia.sigc.hc.persistence.entity.DataType;

public class DataTypeBO extends GenericBO<DataType> {
	
	private static final long serialVersionUID = -906863525507094248L;

	private String name;

	private String description;
	
	private List<DatasetBO> datasets = new ArrayList<>();
	
	public DataTypeBO() {
		super();
	}
	
	public DataTypeBO(DataType dataType) {
		this(dataType, false);
	}

	public DataTypeBO(DataType dataType, boolean eager) {
		super(dataType, eager);
	}

	@Override
	protected void initializeFromEntity(DataType dataType, boolean eager) {
		if (dataType != null) {
			this.setId((Long) dataType.getId());
			this.name = dataType.getName();
			this.description = dataType.getDescription();
			if (eager) {
				this.datasets = dataType.getDatasets().stream().map(DatasetBO::new)
						.collect(Collectors.toList());
			}
		}
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

	public List<DatasetBO> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<DatasetBO> datasets) {
		this.datasets = datasets;
	}
	
	public DataType toEntity() {
		DataType dataType = new DataType();
		dataType.setId(this.getId());
		dataType.setName(this.getName());
		dataType.setDescription(this.getDescription());
		dataType.setVersion(this.getVersion());
		return dataType;
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
				DataTypeBO other = (DataTypeBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}
	
}

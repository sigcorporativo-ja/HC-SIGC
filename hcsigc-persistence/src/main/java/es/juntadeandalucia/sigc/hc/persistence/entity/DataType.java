package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "dataTypes")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataType extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -8553600038158808659L;

	@Id
	@GeneratedValue(generator = "seq_dataType", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_dataType", sequenceName = "seq_x_dataType", allocationSize = 1)
	@Column(name = "x_dataType")
	private Long id;
	
	@NotNull
	@Length(max = 50, message = "{dataType.name.max}")
	@Column(name = "t_name")
	private String name;

	@Length(max = 100, message = "{dataType.description.max}")
	@Column(name = "t_description")
	private String description;
	
	@OneToMany(mappedBy = "dataType")
	private List<Dataset> datasets = new ArrayList<>();
	
	public DataType() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public List<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				DataType other = (DataType) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}
}

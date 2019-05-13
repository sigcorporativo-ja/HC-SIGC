package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "configuration")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Configuration extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -2208297273190406198L;

	@Id
	@GeneratedValue(generator = "seq_conf", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_conf", sequenceName = "seq_x_conf", allocationSize = 1)
	@Column(name = "x_conf")
	private Long id;

	@NotNull
	@Length(max = 100, message = "{configuration.key.max}")
	@Column(name = "t_key", unique=true)
	private String key;

	@Length(max = 250, message = "{configuration.value.max}")
	@Column(name = "t_value")
	private String value;

	@Length(max = 300, message = "{configuration.description.max}")
	@Column(name = "t_description")
	private String description;

	public Configuration() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
				Configuration other = (Configuration) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

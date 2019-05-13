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
@Table(name = "temp_tables")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TempTableRegistry extends SuperclassDomain implements GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3365205750865836430L;
	
	@Id
	@GeneratedValue(generator = "seq_ttre", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_ttre", sequenceName = "seq_x_ttre", allocationSize = 1)
	@Column(name = "x_ttre")
	private Long id;
	
	@NotNull
	@Length(max = 200, message = "{temp_table_registry.name.max}")
	@Column(name = "t_name", unique=true)
	private String name;

	public TempTableRegistry() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

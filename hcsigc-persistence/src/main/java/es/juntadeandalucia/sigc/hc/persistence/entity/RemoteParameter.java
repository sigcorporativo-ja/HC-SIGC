package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "remote_parameters")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteParameter extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -398748697305892792L;

	@Id
	@GeneratedValue(generator = "seq_repa", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_repa", sequenceName = "seq_x_repa", allocationSize = 1)
	@Column(name = "x_repa")
	private Long id;

	@NotNull
	@Length(max = 150, message = "{repa.name.max}")
	@Column(name = "t_name")
	private String name;

	@Length(max = 500, message = "{repa.value.max}")
	@Column(name = "t_value")
	private String value;

	@Column(name = "l_header")
	private boolean header;

	@NotNull
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "reco_x_reco"))
	private RemoteConnection remoteConnection;

	public RemoteParameter() {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean getHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	public RemoteConnection getRemoteConnection() {
		return remoteConnection;
	}

	public void setRemoteConnection(RemoteConnection remoteConnection) {
		this.remoteConnection = remoteConnection;
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
				RemoteParameter other = (RemoteParameter) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

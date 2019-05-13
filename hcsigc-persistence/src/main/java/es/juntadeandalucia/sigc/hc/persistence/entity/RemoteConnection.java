package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
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
@Table(name = "remote_connections")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteConnection extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -2259092840330469157L;

	@Id
	@GeneratedValue(generator = "seq_reco", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_reco", sequenceName = "seq_x_reco", allocationSize = 1)
	@Column(name = "x_reco")
	private Long id;

	@NotNull
	@Length(max = 100, message = "{reco.name.max}")
	@Column(name = "t_name")
	private String name;

	@Length(max = 100, message = "{reco.description.max}")
	@Column(name = "t_description")
	private String description;

	@NotNull
	@Length(max = 250, message = "{reco.url.max}")
	@Column(name = "t_url")
	private String url;

	@Length(max = 50, message = "{reco.method.max}")
	@Column(name = "t_method")
	private String method;

	@OneToMany(mappedBy = "remoteConnection")
	private List<RemoteParameter> remoteConnections;

	@OneToMany(mappedBy = "remoteConnection")
	private List<Dataset> datasets;

	public RemoteConnection() {
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<RemoteParameter> getRemoteConnections() {
		return remoteConnections;
	}

	public void setRemoteConnections(List<RemoteParameter> remoteConnections) {
		this.remoteConnections = remoteConnections;
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
				RemoteConnection other = (RemoteConnection) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

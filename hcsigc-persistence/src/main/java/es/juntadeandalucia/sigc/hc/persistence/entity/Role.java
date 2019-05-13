package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "roles")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Role extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = 6911585816536720568L;

	@Id
	@GeneratedValue(generator = "seq_role", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_role", sequenceName = "seq_x_role", allocationSize = 1)
	@Column(name = "x_role")
	private Long id;

	@NotNull
	@Length(max = 150, message = "{role.name.max}")
	@Column(name = "t_name", unique=true)
	private String name;

	@ManyToMany
	@JoinTable(name = "role_x_perm")
	@CollectionId(
	        columns = @Column(name="x_roper"),
	        type = @Type(type="long"), 
	        generator = "seq_roper"
	    )
	@SequenceGenerator(name = "seq_roper", sequenceName = "seq_x_roper", allocationSize = 1)
	private List<Permission> permissions = new ArrayList<>();

	public Role() {
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

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
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
				Role other = (Role) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

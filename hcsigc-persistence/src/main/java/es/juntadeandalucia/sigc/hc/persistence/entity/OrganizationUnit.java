package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "organizations_units")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationUnit extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = 2643046105542535573L;

	@Id
	@GeneratedValue(generator = "seq_ou", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_ou", sequenceName = "seq_x_ou", allocationSize = 1)
	@Column(name = "x_ou")
	private Long id;

	@NotNull
	@Length(max = 150, message = "{ou.name.max}")
	@Column(name = "t_name", unique=true)
	private String name;

	@Length(max = 250, message = "{ou.name.max}")
	@Column(name = "t_description")
	private String description;

	@OneToMany(mappedBy = "organizationUnit")
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy = "organizationUnit")
	private List<Group> groups = new ArrayList<>();
	
	@OneToMany(mappedBy = "ou", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<UserOUPermission> ouPermissions = new ArrayList<>();


	public OrganizationUnit() {
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<UserOUPermission> getOuPermissions() {
		return ouPermissions;
	}

	public void setOuPermissions(List<UserOUPermission> ouPermissions) {
		this.ouPermissions = ouPermissions;
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
				OrganizationUnit other = (OrganizationUnit) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

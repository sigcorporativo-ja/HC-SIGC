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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
@Table(name = "permissions")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Permission extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = 4725053962124196988L;

	@Id
	@GeneratedValue(generator = "seq_perm", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_perm", sequenceName = "seq_x_perm", allocationSize = 1)
	@Column(name = "x_perm")
	private Long id;

	@NotNull
	@Length(max = 20, message = "{perm.code.max}")
	@Column(name = "t_code", unique=true)
	private String code;

	@NotNull
	@Length(max = 50, message = "{perm.name.max}")
	@Column(name = "t_name")
	private String name;

	@ManyToMany
	@JoinTable(name = "permissions_grant")
	@CollectionId(
	     columns = @Column(name="x_pegr"),
	     generator = "seq_pegr", 
	     type = @Type(type="long")
	    )
	@SequenceGenerator(name = "seq_pegr", sequenceName = "seq_x_pegr", allocationSize = 1)
	private List<Permission> permissionsRequired = new ArrayList<>();

	@ManyToMany(mappedBy = "permissionsRequired")
	private List<Permission> permissionsGranted = new ArrayList<>();

	@ManyToMany(mappedBy = "permissions")
	private List<Role> roles = new ArrayList<>();

	@OneToMany(mappedBy = "permission", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<UserOUPermission> ouPermissions = new ArrayList<>();

	@ManyToMany(mappedBy = "permissions")
	private List<GroupUser> groupsusers = new ArrayList<>();

	public Permission() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissionsRequired() {
		return permissionsRequired;
	}

	public void setPermissionsRequired(List<Permission> permissionsRequired) {
		this.permissionsRequired = permissionsRequired;
	}

	public List<Permission> getPermissionsGranted() {
		return permissionsGranted;
	}

	public void setPermissionsGranted(List<Permission> permissionsGranted) {
		this.permissionsGranted = permissionsGranted;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<UserOUPermission> getOuPermissions() {
		return ouPermissions;
	}

	public void setOuPermissions(List<UserOUPermission> ouPermissions) {
		this.ouPermissions = ouPermissions;
	}

	public List<GroupUser> getGroupsusers() {
		return groupsusers;
	}

	public void setGroupsusers(List<GroupUser> groupsusers) {
		this.groupsusers = groupsusers;
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
				Permission other = (Permission) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

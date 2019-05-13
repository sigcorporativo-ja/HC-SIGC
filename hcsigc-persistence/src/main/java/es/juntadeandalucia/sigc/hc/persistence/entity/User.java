package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;
import org.jasypt.hibernate4.type.EncryptedStringType;

@TypeDef(
	name="encryptedString",
    typeClass=EncryptedStringType.class,
    parameters= {
        @Parameter(name="encryptorRegisteredName", value="defaultStringEncryptor")
    }
)
@Entity
@Table(name = "users")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -1857727014840730132L;

	@Id
	@GeneratedValue(generator = "seq_user", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_user", sequenceName = "seq_x_user", allocationSize = 1)
	@Column(name = "x_user")
	private Long id;

	@Length(max = 150, message = "{user.name.max}")
	@Column(name = "t_name")
	private String name;

	@Length(max = 150, message = "{user.surname.max}")
	@Column(name = "t_surname")
	private String surname;

	@NotNull
	@Length(max = 150, message = "{user.mail.max}")
	@Column(name = "t_mail", unique = true)
	private String mail;

	@NotNull
	@Length(max = 150, message = "{user.username.max}")
	@Column(name = "t_username", unique = true)
	private String username;

	@Type(type = "encryptedString")
	@Length(max = 150, message = "{user.password.max}")
	@Column(name = "t_password")
	private String password;

	@Column(name = "l_ldap")
	private boolean ldap;

	@NotNull
	@Column(name = "n_quota")
	private Long quota;

	@Column(name = "n_used_quota")
	private Long usedQuota;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey = @ForeignKey(name = "ou_x_ou"))
	private OrganizationUnit organizationUnit;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<UserOUPermission> ouPermissions = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<GroupUser> groupsUsers = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Dataset> datasets = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "favorites_x_datasets")
	@CollectionId(
	        columns = @Column(name="x_favdata"),
	        type = @Type(type="long"), 
	        generator = "seq_favdata"
	    )
	@SequenceGenerator(name = "seq_favdata", sequenceName = "seq_x_favdata", allocationSize = 1)
	private List<Dataset> favorites = new ArrayList<>();

	public User() {
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLdap() {
		return ldap;
	}

	public Long getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Long usedQuota) {
		this.usedQuota = usedQuota;
	}

	public void setLdap(boolean ldap) {
		this.ldap = ldap;
	}

	public Long getQuota() {
		return quota;
	}

	public void setQuota(Long quota) {
		this.quota = quota;
	}

	public OrganizationUnit getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(OrganizationUnit organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public List<UserOUPermission> getOuPermissions() {
		return ouPermissions;
	}

	public void setOuPermissions(List<UserOUPermission> ouPermissions) {
		this.ouPermissions = ouPermissions;
	}

	public List<GroupUser> getGroupsUsers() {
		return groupsUsers;
	}

	public void setGroupsUsers(List<GroupUser> groupsUsers) {
		this.groupsUsers = groupsUsers;
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
				User other = (User) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

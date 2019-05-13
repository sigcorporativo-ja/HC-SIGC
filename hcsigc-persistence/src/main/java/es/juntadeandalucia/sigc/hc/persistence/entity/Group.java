package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.ForeignKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "groups")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Group extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -1938369902169930961L;

	@Id
	@GeneratedValue(generator = "seq_group", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_group", sequenceName = "seq_x_group", allocationSize = 1)
	@Column(name = "x_group")
	private Long id;

	@NotNull
	@Length(max = 50, message = "{group.name.max}")
	@Column(name = "t_name", unique=true)
	private String name;

	@Length(max = 100, message = "{group.description.max}")
	@Column(name = "t_description")
	private String description;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey = @ForeignKey(name = "ou_x_ou"))
	private OrganizationUnit organizationUnit;

	@ManyToMany
	@JoinTable(
			name = "dbconn_x_groups",
			joinColumns=@JoinColumn(name="groups_x_group", referencedColumnName="x_group"),
			inverseJoinColumns=@JoinColumn(name="dbconnections_x_dbconn", referencedColumnName="x_dbconn")
			
	)
	@CollectionId(
	        columns = @Column(name="x_dbgr"),
	        generator = "seq_dbgr", 
	        type = @Type(type="long")
	    )
	@SequenceGenerator(name = "seq_dbgr", sequenceName = "seq_x_dbgr", allocationSize = 1)
	private List<DBConnection> dbconnections = new ArrayList<>();

	@OneToMany(mappedBy = "group")
	private List<GroupUser> groupsUsers = new ArrayList<>();

	public Group() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrganizationUnit getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationunit(OrganizationUnit organizationUnit) {
		this.organizationUnit = organizationUnit;
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

	public List<DBConnection> getDbconnections() {
		return dbconnections;
	}

	public void setDbconnections(List<DBConnection> dbconnections) {
		this.dbconnections = dbconnections;
	}

	public List<GroupUser> getGroupsUsers() {
		return groupsUsers;
	}

	public void setGroupsUsers(List<GroupUser> groupsUsers) {
		this.groupsUsers = groupsUsers;
	}

	public void setOrganizationUnit(OrganizationUnit organizationUnit) {
		this.organizationUnit = organizationUnit;
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
				Group other = (Group) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

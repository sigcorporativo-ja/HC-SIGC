package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "groups_x_users")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupUser extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -9150867257573132830L;

	@Id
	@GeneratedValue(generator = "seq_grus", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_grus", sequenceName = "seq_x_grus", allocationSize = 1)
	@Column(name = "x_grus")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "grou_x_grou"))
	private Group group;

	@NotNull
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "user_x_user"))
	private User user;

	@ManyToMany
	@JoinTable(name = "data_x_grus")
	@CollectionId(
	        columns = @Column(name="x_dgus"),
	        generator = "seq_dgus", 
	        type = @Type(type="long")
	    )
	@SequenceGenerator(name = "seq_dgus", sequenceName = "seq_x_dgus", allocationSize = 1)
	private List<Dataset> datasets = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "grus_x_perm")
	@CollectionId(
	        columns = @Column(name="x_grpe"),
	        generator = "seq_grpe", 
	        type = @Type(type="long")
	    )
	@SequenceGenerator(name = "seq_grpe", sequenceName = "seq_x_grpe", allocationSize = 1)
	private List<Permission> permissions = new ArrayList<>();

	public GroupUser() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
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
				GroupUser other = (GroupUser) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

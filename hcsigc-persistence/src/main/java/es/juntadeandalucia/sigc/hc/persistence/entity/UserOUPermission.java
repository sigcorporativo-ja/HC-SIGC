package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "user_x_uo_x_perm")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserOUPermission extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = -2476821168515628742L;

	@Id
	@GeneratedValue(generator = "seq_uope", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_uope", sequenceName = "seq_x_uope", allocationSize = 1)
	@Column(name = "x_uope")
	private Long id;
	
	@NotNull
	@ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user_x_user")
	private User user;
	
	@NotNull
	@ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "ou_x_ou")
	private OrganizationUnit ou;
	
	@NotNull
	@ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "perm_x_perm")
	private Permission permission;
	
	public UserOUPermission() {
		super();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrganizationUnit getOu() {
		return ou;
	}

	public void setOu(OrganizationUnit ou) {
		this.ou = ou;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

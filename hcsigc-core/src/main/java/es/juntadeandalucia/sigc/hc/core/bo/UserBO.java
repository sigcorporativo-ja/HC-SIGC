package es.juntadeandalucia.sigc.hc.core.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.Partition;

import es.juntadeandalucia.sigc.hc.persistence.entity.User;

public class UserBO implements Account, Serializable {

	private static final long serialVersionUID = -2333146064251328596L;

	private String id;
	private Long version;
	
	private String name;
	private String surname;
	private String mail;
	private String username;
	private String password;
	private boolean ldap;
	private Long quota;
	private Long usedQuota;
	private OrganizationUnitBO organizationUnit;
	private List<PermissionBO> permissions = new ArrayList<>();
	private List<GroupUserBO> groups = new ArrayList<>();

	private Date createdDate;
	private Date expirationDate;
	private Partition partition;
	private boolean enabled = true;
	private Collection<Attribute<? extends Serializable>> attributes;

	public UserBO() {
	}

	public UserBO(User user) {
		this(user, false);
	}

	public UserBO(User user, boolean eager) {
		this.initializeFromEntity(user, eager);
	}

	private void initializeFromEntity(User user, boolean eager) {
		if (user != null) {
			this.setId(String.valueOf(user.getId()));
			this.name = user.getName();
			this.surname = user.getSurname();
			this.mail = user.getMail();
			this.username = user.getUsername();
			this.password = user.getPassword();
			this.quota = user.getQuota();
			this.usedQuota = user.getUsedQuota();
			this.organizationUnit = new OrganizationUnitBO(user.getOrganizationUnit());
			if (eager) {
				this.permissions = user.getOuPermissions().stream()
						.map(ouPerm -> new PermissionBO(ouPerm.getPermission())).collect(Collectors.toList());
			}
			this.setVersion(user.getVersion());
			inicializeAttributes();
		}
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

	public void setLdap(boolean ldap) {
		this.ldap = ldap;
	}

	public Long getQuota() {
		return quota;
	}

	public void setQuota(Long quota) {
		this.quota = quota;
	}

	public Long getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Long usedQuota) {
		this.usedQuota = usedQuota;
	}

	public OrganizationUnitBO getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(OrganizationUnitBO organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public List<PermissionBO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionBO> permissions) {
		this.permissions = permissions;
	}

	public List<GroupUserBO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupUserBO> groups) {
		this.groups = groups;
	}

	public User toEntity() {
		User user = new User();
		String boId = this.getId();
		if (boId != null) {
			user.setId(Long.parseLong(boId));
		}
		user.setVersion(this.getVersion());
		user.setName(this.getName());
		user.setSurname(this.getSurname());
		user.setMail(this.getMail());
		user.setUsername(this.getUsername());
		user.setPassword(this.getPassword());
		user.setQuota(this.getQuota());
		user.setUsedQuota(this.getUsedQuota());
		
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long idLong = Long.parseLong(getId());
		result = prime * result + (int) (idLong ^ (idLong >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				UserBO other = (UserBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public Partition getPartition() {
		return partition;
	}

	@Override
	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	@Override
	public void setAttribute(Attribute<? extends Serializable> attribute) {
		removeAttribute(attribute.getName());
		this.attributes.add(attribute);
	}

	@Override
	public void removeAttribute(String name) {
		Iterator<Attribute<? extends Serializable>> it = this.attributes.iterator();
		while (it.hasNext()) {
			if (name.equals(it.next().getName())) {
				it.remove();
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> Attribute<T> getAttribute(String name) {
		Attribute<T> attribute = null;
		if(this.attributes != null) {
			Iterator<Attribute<? extends Serializable>> it = this.attributes.iterator();
			while (it.hasNext()) {
				Attribute<? extends Serializable> currAttribute = it.next();
				if (name.equals(currAttribute.getName())) {
					attribute = (Attribute<T>) currAttribute;
					break;
				}
			}
		}
		return attribute;
	}

	@Override
	public Collection<Attribute<? extends Serializable>> getAttributes() {
		return attributes;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	private void inicializeAttributes() {
		attributes = new ArrayList<>();
		setAttribute(new Attribute<String>("username",this.username));
	}
}
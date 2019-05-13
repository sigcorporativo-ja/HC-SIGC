package es.juntadeandalucia.sigc.hc.core.bo;

import java.io.Serializable;

import es.juntadeandalucia.sigc.hc.persistence.entity.SuperclassDomain;

public abstract class GenericBO<T extends SuperclassDomain> implements Serializable {

	private static final long serialVersionUID = 886324463425952788L;
	
	private Long id;
	private Long version;

	public GenericBO() {}
	
	public GenericBO(T entity) {
		this(entity, false);
	}
	
	public GenericBO(T entity, boolean eager) {
		this.initializeFromEntity(entity, eager);
		this.setVersion(entity.getVersion());
	}
	
	protected abstract void initializeFromEntity(T entity, boolean eager);

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}

/* Copyright 2015 Guadaltel, S.A.
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@EntityListeners({ es.juntadeandalucia.sigc.hc.persistence.listener.AuditListener.class })
public class SuperclassDomain implements Serializable {

	private static final long serialVersionUID = 620035322494978784L;

	@NotNull
	@Version
	private Long version;

	@Column(name = "t_created")
	private String created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "d_creation_date")
	private Date creationDate;

	@Column(name = "t_modified")
	private String modified;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "d_modification_date")
	private Date modificationDate;

	/*
	 * JPA EVENTS
	 */

	@PrePersist
	public void beforeInsert() {
		this.creationDate = new Date();
	}

	@PreUpdate
	public void beforeUpdate() {
		this.modificationDate = new Date();
	}
		
	
	/*
	 * GETTERS && SETTERS
	 */

	public String getCreated() {

		return created;
	}

	public void setCreated(String created) {

		this.created = created;
	}

	public Date getCreationDate() {

		return creationDate;
	}

	public void setCreationDate(Date creationDate) {

		this.creationDate = creationDate;
	}

	public String getModified() {

		return modified;
	}

	public void setModified(String modified) {

		this.modified = modified;
	}

	public Date getModificationDate() {

		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {

		this.modificationDate = modificationDate;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getVersion() {
		return this.version;
	}
}

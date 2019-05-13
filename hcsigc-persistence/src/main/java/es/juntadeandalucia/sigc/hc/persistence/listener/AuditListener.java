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
package es.juntadeandalucia.sigc.hc.persistence.listener;

import java.util.Date;

import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.picketlink.Identity;

import es.juntadeandalucia.sigc.hc.persistence.entity.SuperclassDomain;
import es.juntadeandalucia.sigc.hc.persistence.utils.BeanManagerUtils;

@Stateless
public class AuditListener {

	@PrePersist
	public void prePersist(SuperclassDomain object) throws NamingException {

		object.setCreationDate(new Date());
		Identity identity = BeanManagerUtils.inject(Identity.class);
		if (identity.isLoggedIn()) {
    		object.setCreated((String) identity.getAccount().getAttribute("username").getValue());
		} else {
			object.setCreated("PUBLIC");
		}
	}

	@PreUpdate
	public void preUpdate(SuperclassDomain object) throws NamingException {

		object.setModificationDate(new Date());
		
		Identity identity = BeanManagerUtils.inject(Identity.class);
		if(identity.isLoggedIn()) {
			object.setModified((String) identity.getAccount().getAttribute("username").getValue());
		} else {
			object.setModified("PUBLIC");
		}
	}

}

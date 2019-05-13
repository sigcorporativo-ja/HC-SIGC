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
package es.juntadeandalucia.sigc.hc.core.impl.log;

import java.io.Serializable;
import java.util.UUID;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("logger")
@SessionScoped
public class Logger implements Serializable {

    private static final long serialVersionUID = -5745711217229353626L;

	private String ticket = "TICKET_" + UUID.randomUUID(); 
	
    public String getTicket() {
    	return ticket;
    }
	
    public void setTicket(String ticket) {
    	this.ticket = ticket;
    }
}

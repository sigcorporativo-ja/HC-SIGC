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
package es.juntadeandalucia.sigc.hc.core.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.picketlink.Identity;

import es.juntadeandalucia.sigc.hc.core.interceptor.binding.UserLoggedIn;
import es.juntadeandalucia.sigc.hc.core.interceptor.exception.NotLoggedInException;

@Interceptor
@UserLoggedIn
public class LoggedUserInterceptor implements Serializable {

	private static final long serialVersionUID = 4726406902768824437L;

	@Inject
	private Identity identity;
	
	
	@AroundInvoke
	public Object logMethodEntry(InvocationContext ctx) throws Exception {
		if (identity.isLoggedIn()) {
			return ctx.proceed();
		} else {
			throw new NotLoggedInException("User not logged");
		}
	}
}
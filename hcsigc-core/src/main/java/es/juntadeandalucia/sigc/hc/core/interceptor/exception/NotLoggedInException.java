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
package es.juntadeandalucia.sigc.hc.core.interceptor.exception;

public class NotLoggedInException extends Exception {

	private static final long serialVersionUID = -7789507571640142884L;
	
	public NotLoggedInException() {
		super();
	}
	
	public NotLoggedInException(String message, Throwable e) {
		super(message, e);
	}
	
	public NotLoggedInException(String message) {
		super(message);
	}
	
	public NotLoggedInException(Throwable e) {
		super(e);
	}
}

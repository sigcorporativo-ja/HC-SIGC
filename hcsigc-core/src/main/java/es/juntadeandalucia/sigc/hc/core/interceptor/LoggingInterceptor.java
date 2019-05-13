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
import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.interceptor.binding.Audit;

@Interceptor
@Audit
/**
 * Interceptor encargado de trazar las peticiones a los beans de la aplicación. 
 * Estas peticiones para perjudicar al rendimiento de la aplicación sólo aparecerán 
 * a nivel de log TRACE.
 * 
 * La información contenida en las traza es:
 *   - Clase de invocación
 *   - Método de invocación
 *   - Tiempo de ejecución del método
 */
public class LoggingInterceptor implements Serializable {

	private static final long serialVersionUID = -3768380838719016556L;

	private static final Log LOG = LogFactory.getLog(LoggingInterceptor.class);

	@AroundInvoke
	public Object checkUSerLogged(InvocationContext ctx) throws Exception {

		long start = System.currentTimeMillis();

		if (LOG.isTraceEnabled()) {
			LOG.trace("target " + ctx.getTarget().getClass());
			LOG.trace("method "
			        + ctx.getMethod().getName()
			        + " signature "
			        + ctx.getMethod()
			        + " with annotations "
			        + Arrays.toString(ctx.getMethod().getAnnotations()));

			Annotation[][] parameterAnnotations = ctx.getMethod().getParameterAnnotations();
			Object[] parameterValues = ctx.getParameters();
			Class<?>[] parameterTypes = ctx.getMethod().getParameterTypes();

			for (int index = 0; index < parameterValues.length; index++) {
				LOG.trace("param "
				        + index
				        + " value="
				        + parameterValues[index]
				        + " type="
				        + parameterTypes[index]
				        + " annotations="
				        + Arrays.toString(parameterAnnotations[index]));
			}
		}
		Object result = ctx.proceed();
		
		if (LOG.isTraceEnabled()) {
			LOG.trace("Execution time: " + (System.currentTimeMillis() - start) + " milis");
		}
		
		return result;
	}
}

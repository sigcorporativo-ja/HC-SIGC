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
package es.juntadeandalucia.sigc.hc.persistence.utils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * This class helps you to find EJB3 beans using JNDI with the Weld BeanManager.
 */
public class BeanManagerUtils {

	private static final String BEANMANAGER_JNDI_NAME = "java:comp/BeanManager";

	private BeanManagerUtils() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T inject(Class<T> interfaceClass) throws NamingException {
		BeanManager bm = (BeanManager) InitialContext.doLookup(BEANMANAGER_JNDI_NAME);
		Bean bean = bm.getBeans(interfaceClass).iterator().next();
		CreationalContext ctx = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, interfaceClass, ctx);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T inject(String beanName) throws NamingException {
		BeanManager bm = (BeanManager) InitialContext.doLookup(BEANMANAGER_JNDI_NAME);
		Bean bean = bm.getBeans(beanName).iterator().next();
		CreationalContext ctx = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, bean.getBeanClass(), ctx);
	}

	public static <T> void programmaticOutjection(Class<T> clazz, T outjectionObject) throws NamingException {
		InitialContext initialContext = new InitialContext();
		Object lookup = initialContext.lookup(BEANMANAGER_JNDI_NAME);
		BeanManager beanManager = (BeanManager) lookup;
		AnnotatedType<T> annotatedType = beanManager.createAnnotatedType(clazz);
		InjectionTarget<T> injectionTarget = beanManager.createInjectionTarget(annotatedType);
		CreationalContext<T> creationalContext = beanManager.createCreationalContext(null);
		injectionTarget.inject(outjectionObject, creationalContext);
		creationalContext.release();
	}

	public static <T> T injectGlobal(String jndiName) throws NamingException {
		return InitialContext.doLookup(jndiName);
	}

}

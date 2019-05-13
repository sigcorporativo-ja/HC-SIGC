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
package es.juntadeandalucia.sigc.hc.core.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.guadaltel.framework.core.utils.PropertyLoader;

@ApplicationScoped
public class ConfigurationFactory {

	private static final Log LOG = LogFactory.getLog(ConfigurationFactory.class);

	private Properties properties;

	@PostConstruct
	public void init() {

		LOG.info("Cargando configuracion de la aplicacion");
		properties = PropertyLoader.loadProperties("config");
	}

	@Produces
	@Configuration
	public String getConfigValue(InjectionPoint ip) {

		Configuration config = ip.getAnnotated().getAnnotation(Configuration.class);
		String configKey = config.value();

		LOG.debug("Obteniendo propiedad " + configKey);

		if (configKey.isEmpty()) {

			throw new IllegalArgumentException("Properties value key is required.");
		}

		return properties.getProperty(configKey);
	}
}

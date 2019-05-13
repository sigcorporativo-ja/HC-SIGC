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

public class ConfigurationConstants {
    
	public static final String TREWA_SYSTEM = "trewa.system";
	
	public static final String JAVAMAIL_SERVER = "javamail.server";
	
	public static final String JAVAMAIL_PORT = "javamail.port";
	
	public static final String JAVAMAIL_USERNAME = "javamail.username";
	
	public static final String JAVAMAIL_PASSWORD = "javamail.password"; // NOSONAR: no es un password hardcodeado
	
	/** Constante que indica si se usara configurator */
	public static final String CONFIGURATOR_GCONFG_ACTIVATED = "core.configurator.activated";
    
	public static final String CONFIGURATOR_GCONFG_APP = "core.configurator.application";
    
	public static final String CONFIGURATOR_GCONFG_DEPLOY = "core.configurator.deployment";
    
	public static final String CONFIGURATOR_GCONFG_PATH = "core.configurator.path";
    
	public static final String CONFIGURATOR_GCONFG_FOLDER_TMP = "core.configutator.lastGoodConfig.dir";
    
	public static final String CONFIGURATOR_GCONFG_CACHE = "core.configurator.cache";
    
	public static final String CONFIGURATOR_GCONFG_CACHE_TIMELIVE = "core.configurator.cache.timeToLive";
    
	public static final String CONFIGURATOR_GCONFG_CACHE_TIMETOID = "core.configurator.cache.timeToIdle";
    
	public static final String CONFIGURATOR_GCONFG_CACHE_JMX_SERVICE = "core.configurator.cache.jmx.serviceUrl";
    
	public static final String CONFIGURATOR_CACHE_MANAGER = "net.sf.ehcache:type=CacheManager,name=CONFIGURATOR_CACHE_MANAGER";

	private ConfigurationConstants() {
	}
}

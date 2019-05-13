package es.juntadeandalucia.sigc.hc.core.configuration;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * WS client configuration
 * @author Guadaltel S.A
 *
 */
public abstract class WSClientConfiguration {
	
	private static final Logger log = Logger.getLogger(WSClientConfiguration.class.getName());
	
	private static final String CONFIG_FILE_PREFIX = "env.";
	
	private static final String FILE_NAME = "ws-config";
	
	public static final String WS_CONTENT_TYPE = "application/json";
	
	public static final String WS_URI = getReadedProperty("ws.url.rest");
	
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	// Implicit constructor
	private WSClientConfiguration() {
		// empty constructor
	}
	
	/**
	 * Read properties from resource bundle
	 * @param name : property key
	 * @return @{link String} with property value
	 */
	private static String getReadedProperty(String name) {
		try {
			ResourceBundle bund = ResourceBundle.getBundle(FILE_NAME);
			return bund.getString(CONFIG_FILE_PREFIX +  name);
		} catch (Exception e) {
			log.error("Error at read properties file " + FILE_NAME, e);
			return new String();
		}
	}
	
}

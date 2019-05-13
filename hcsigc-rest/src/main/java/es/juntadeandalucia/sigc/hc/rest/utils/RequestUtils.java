package es.juntadeandalucia.sigc.hc.rest.utils;

import java.util.Locale;

public class RequestUtils {
	
	public static String validateLocale(Locale locale) {
		return locale.toString().split("_")[0];
	}

}

package es.juntadeandalucia.sigc.hc.core.file.impl;

import java.io.InputStream;

import es.juntadeandalucia.sigc.hc.core.file.IData;

public abstract class DataAbstract<T extends Iterable<String>> implements IData<T> {

	protected InputStream data;
	
	protected DataAbstract(InputStream data) {
		this.data = data;
	}
}

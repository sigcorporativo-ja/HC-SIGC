package es.juntadeandalucia.sigc.hc.core.file.impl;

import es.juntadeandalucia.sigc.hc.core.file.IConnector;
import es.juntadeandalucia.sigc.hc.core.file.IData;

public abstract class ConnectorAbstract<T extends Iterable<String>> implements IConnector<T> {

	private static final int BATCH_SIZE_DEFAULT = 10;
	
	protected int batchSize;
	protected IData<T> data;
	
	protected ConnectorAbstract(IData<T> data) {
		this(data, BATCH_SIZE_DEFAULT);
	}
	
	protected ConnectorAbstract(IData<T> data, int batchSize) {
		this.data = data;
		this.batchSize = batchSize;
	}
}

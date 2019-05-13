package es.juntadeandalucia.sigc.hc.core.file.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import es.juntadeandalucia.sigc.hc.core.file.IData;

public class LocalConnector<T extends Iterable<String>> extends ConnectorAbstract<T> {

	public LocalConnector(IData<T> data) {
		super(data);
	}
	
	public LocalConnector(IData<T> data, int batchSize) {
		super(data, batchSize);
	}

	@Override
	public T getAttributeNames() throws IOException {
		return this.data.getAttributesNames();
	}

	@Override
	public boolean hasAvailableData() throws IOException {
		return this.data.hasAvailableData();
	}

	@Override
	public Collection<T> getBatch() throws IOException {
		Collection<T> batch = new ArrayList<>();
		for (int i = 0; i < this.batchSize && hasAvailableData(); i++) {
			T values = getValues();
			batch.add(values);
		}
		return batch;
	}
	
	protected T getValues() throws IOException {
		return this.data.getValues();
	}
}

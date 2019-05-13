package es.juntadeandalucia.sigc.hc.core.file;

import java.io.IOException;
import java.util.Collection;

public interface IConnector<T extends Iterable<String>> {

	T getAttributeNames() throws IOException;

	boolean hasAvailableData() throws IOException;

	Collection<T> getBatch() throws IOException;

}

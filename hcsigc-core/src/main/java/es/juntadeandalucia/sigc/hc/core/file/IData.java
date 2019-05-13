package es.juntadeandalucia.sigc.hc.core.file;

import java.io.IOException;

public interface IData<T extends Iterable<String>> {
	enum Type {
		CSV,
		TEXT,
		SHAPEFILE,
		GEOJSON,
		GML,
		KML,
		TOPOJSON,
		GPX
	}

	T getAttributesNames() throws IOException;

	T getValues() throws IOException;

	boolean hasAvailableData() throws IOException;
}

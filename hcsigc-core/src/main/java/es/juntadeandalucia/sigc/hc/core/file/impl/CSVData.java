package es.juntadeandalucia.sigc.hc.core.file.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CSVData extends DataAbstract<List<String>> {

	private static final char DEFAULT_SEPARATOR = ',';
	private static final boolean DEFAULT_IGNORE_QUOTATIONS = false;
	private static final int DEFAULT_SKIP_LINES = 0;
	
	private CSVReader reader;
	
	public CSVData(InputStream data) {
		this(data, DEFAULT_SEPARATOR, DEFAULT_IGNORE_QUOTATIONS, DEFAULT_SKIP_LINES);
	}
	
	public CSVData(InputStream data, char separator, boolean igonreQuotations, int skipLines) {
		super(data);
		CSVParser parser = new CSVParserBuilder().withSeparator(separator).withIgnoreQuotations(igonreQuotations).build();
		this.reader = new CSVReaderBuilder(new InputStreamReader(data)).withSkipLines(skipLines).withCSVParser(parser).build();
	}

	@Override
	public List<String> getAttributesNames() throws IOException {
		List<String> attributesNames;
		// the first line is the header
		if ((this.reader.getLinesRead() == 0) && hasAvailableData()) {
			attributesNames = this.getValues();
		}
		else {
			attributesNames = null;
		}
		return attributesNames;
	}

	@Override
	public List<String> getValues() throws IOException {
		List<String> values;
		
		String[] arrValues = this.reader.readNext();
		if (arrValues != null) {
			values = Arrays.asList(arrValues);
		}
		else {
			values = null;
		}
		return values;
	}

	@Override
	public boolean hasAvailableData() throws IOException {
		return (this.reader.peek() != null);
	}
}

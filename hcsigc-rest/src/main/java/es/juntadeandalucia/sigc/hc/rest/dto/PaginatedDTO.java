package es.juntadeandalucia.sigc.hc.rest.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.GenericBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;

public class PaginatedDTO<T extends GenericDTO<K>, K extends GenericBO<?>> {

	@JsonProperty("count")
	private long count;

	@JsonProperty("data")
	private List<T> data;

	/**
	 * Default constructor
	 */
	public PaginatedDTO() {
		super();
		this.data = new LinkedList<>();
	}

	public PaginatedDTO(PaginatedBO<K> paginatedBO, Class<T> dtoClass, Class<K> boClass) {
		this();
		this.count = paginatedBO.getCount();
		
		for(K bo : paginatedBO.getData()) {
			try {
				T dto = dtoClass.getDeclaredConstructor(boClass).newInstance(bo);
				this.data.add(dto);
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| SecurityException | InvocationTargetException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}

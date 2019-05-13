package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.List;

import es.juntadeandalucia.sigc.hc.persistence.entity.SuperclassDomain;

public class PaginatedBO<T extends GenericBO<? extends SuperclassDomain>> {
	private long count;
	private List<T> data;
	
	public PaginatedBO() {
		super();
	}
	public PaginatedBO(long count, List<T> data) {
		this.count = count;
		this.data = data;
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

package es.juntadeandalucia.sigc.hc.core.bo;

import java.util.List;

public class UserPaginatedBO {
	private long count;
	private List<UserBO> data;
	
	public UserPaginatedBO() {
		super();
	}
	public UserPaginatedBO(long count, List<UserBO> data) {
		this.count = count;
		this.data = data;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<UserBO> getData() {
		return data;
	}
	public void setData(List<UserBO> data) {
		this.data = data;
	}
}

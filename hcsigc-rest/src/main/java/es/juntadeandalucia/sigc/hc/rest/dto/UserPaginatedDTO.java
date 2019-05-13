package es.juntadeandalucia.sigc.hc.rest.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juntadeandalucia.sigc.hc.core.bo.UserPaginatedBO;

public class UserPaginatedDTO {

	@JsonProperty("count")
	private long count;

	@JsonProperty("data")
	private List<UserDTO> data;

	/**
	 * Default constructor
	 */
	public UserPaginatedDTO() {
		super();
		this.data = new LinkedList<>();
	}

	public UserPaginatedDTO(UserPaginatedBO paginatedBO) {
		this.data = paginatedBO.getData().stream().map(UserDTO::new).collect(Collectors.toList());
		this.count = paginatedBO.getCount();
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<UserDTO> getData() {
		return data;
	}

	public void setData(List<UserDTO> data) {
		this.data = data;
	}

}

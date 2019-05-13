package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.DBMSBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;

public interface IDBMSController extends Serializable {

	List<DBMSBO> getAllDBMS();

	public PaginatedBO<DBMSBO>paginate(int start, int size);

	void createDBMS(DBMSBO dbmsBO);
	void updateDBMS(DBMSBO dbmsBO);
	void removeDBMS(DBMSBO dbmsBO);
	DBMSBO findById(Long id);
	
	public PaginatedBO<DBMSBO> findByField(String field, String match);
	
	PaginatedBO<DBMSBO> search(int start, int size, String match, String searchBy);
}

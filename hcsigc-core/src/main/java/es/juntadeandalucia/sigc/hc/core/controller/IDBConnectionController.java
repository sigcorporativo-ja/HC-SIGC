package es.juntadeandalucia.sigc.hc.core.controller;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.DBConnectionBO;
import es.juntadeandalucia.sigc.hc.core.bo.PaginatedBO;

public interface IDBConnectionController extends Serializable {

	List<DBConnectionBO> getAllDBConnections();

	public PaginatedBO<DBConnectionBO> paginate(int start, int size);

	void createDBConnection(DBConnectionBO dbConnectionBO);

	void updateDBConnection(DBConnectionBO dbConnectionBO);

	void removeDBConnection(DBConnectionBO dbConnectionBO);

	DBConnectionBO findById(Long id);
	
	public PaginatedBO<DBConnectionBO> findByField(String field, String match);
	
	PaginatedBO<DBConnectionBO> search(int start, int size, String match, String searchBy);
}

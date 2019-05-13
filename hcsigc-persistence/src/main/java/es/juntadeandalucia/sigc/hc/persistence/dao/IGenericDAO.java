/* Copyright 2015 Guadaltel, S.A.
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package es.juntadeandalucia.sigc.hc.persistence.dao;

import java.io.Serializable;
import java.util.List;

import es.juntadeandalucia.sigc.hc.persistence.entity.GenericEntity;

public interface IGenericDAO<T extends GenericEntity, ID extends Serializable> extends Serializable {
	
	T findById(ID id);
	
	List<T> findAll();

	T save(T entity);

	void remove(T entity);

	Long create(T entity);

	Long count();

	List<T> paginate(int start , int size);
	
	List<T> findByField(String field, String match);
	
	List<T> search(int start, int size, String match, String queryAtributes);

	Long countSearch(String match, String queryAtributes);

	List<T> paginateByOrganizationUnit(int start, int size, String ou);

	Long countByOrganizationUnit(String ou);

	List<T> searchByOrganizationUnit(int start, int size, String match, String queryAtributes, String ou);

	Long countSearchByOrganizationUnit(String match, String queryAtributes, String ou);
}

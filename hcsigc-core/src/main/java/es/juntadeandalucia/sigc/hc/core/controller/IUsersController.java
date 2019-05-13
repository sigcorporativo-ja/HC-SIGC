package es.juntadeandalucia.sigc.hc.core.controller;

import java.util.List;

import es.juntadeandalucia.sigc.hc.core.bo.UserBO;

public interface IUsersController {

	UserBO getUserByUsername(String username);

	List<UserBO> findAll();

	UserBO findById(Long usuarioId);

	UserBO create(UserBO bo);

	UserBO update(UserBO bo);

	void remove(UserBO userAux);

	UserBO findByNameAndPassword(String username, String password);

	boolean hasPermissions(UserBO userSession, String[] requiredPermissions);

}

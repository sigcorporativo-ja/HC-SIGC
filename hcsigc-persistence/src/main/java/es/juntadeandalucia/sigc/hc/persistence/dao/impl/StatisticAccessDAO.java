package es.juntadeandalucia.sigc.hc.persistence.dao.impl;

import javax.ejb.Stateless;

import es.juntadeandalucia.sigc.hc.persistence.dao.IStatisticAccessDAO;
import es.juntadeandalucia.sigc.hc.persistence.entity.StatisticAccess;

@Stateless
public class StatisticAccessDAO extends GenericDAO<StatisticAccess, Integer> implements IStatisticAccessDAO {

	private static final long serialVersionUID = -8689641555178434055L;

}

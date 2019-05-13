package es.juntadeandalucia.sigc.hc.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "statistics_access")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticAccess extends SuperclassDomain implements GenericEntity {

	private static final long serialVersionUID = 215884646626796833L;

	@Id
	@GeneratedValue(generator = "seq_statistic", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_statistic", sequenceName = "seq_x_statistic", allocationSize = 1)
	@Column(name = "x_statistic")
	private Long id;

	@Column(name = "t_last_access")
	private Date lastAccess;

	@Column(name = "n_count")
	private int count;

	public StatisticAccess() {
		super();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStatisticAccess() {
		return lastAccess;
	}

	public void setStatisticAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				StatisticAccess other = (StatisticAccess) obj;
				if (id != other.id) {
					result = false;
				}
			}
		}
		return result;
	}

}

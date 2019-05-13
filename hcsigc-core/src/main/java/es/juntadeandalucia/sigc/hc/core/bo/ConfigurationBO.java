package es.juntadeandalucia.sigc.hc.core.bo;

import es.juntadeandalucia.sigc.hc.persistence.entity.Configuration;

public class ConfigurationBO extends GenericBO<Configuration> {

	private static final long serialVersionUID = 637769064911185131L;

	private String key;

	private String value;

	private String description;

	public ConfigurationBO() {
		super();
	}

	public ConfigurationBO(Configuration config) {
		this(config, false);
	}

	public ConfigurationBO(Configuration configuration, boolean eager) {
		super(configuration, eager);
	}

	@Override
	protected void initializeFromEntity(Configuration entity, boolean eager) {
		if (entity != null) {
			this.setId((Long) entity.getId());
			this.key = entity.getKey();
			this.value = entity.getValue();
			this.description = entity.getDescription();
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Configuration toEntity() {
		Configuration config = new Configuration();
		config.setId(this.getId());
		config.setKey(this.getKey());
		config.setValue(this.getValue());
		config.setDescription(this.getDescription());
		return config;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null || getClass() != obj.getClass()) {
				result = false;
			} else {
				ConfigurationBO other = (ConfigurationBO) obj;
				if (getId() != other.getId()) {
					result = false;
				}
			}
		}
		return result;

	}

}
package es.guadaltel.archetype.reveng;

import java.beans.Introspector;
import java.util.List;
import java.util.Properties;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringSettings;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategyUtil;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.mapping.Column;

public class GuadaltelReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

	//Establecer el prefijo de las tablas, si procede
	private static final String PREFIX = "PREFIX_";
	
	private ReverseEngineeringSettings settings;

	public GuadaltelReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}
	
	public void setSettings(ReverseEngineeringSettings settings) {
		this.settings = settings;
	}

	public String tableToClassName(TableIdentifier tableIdentifier) {
		String pkgName = settings.getDefaultPackageName();
		String tableName = tableIdentifier.getName();
		if (tableName.startsWith(PREFIX)) {
			tableName = tableName.substring(PREFIX.length());
			if (tableName.endsWith("ES")) {
				tableName = tableName.substring(0, tableName.length() - 2);
			} else if (tableName.endsWith("S")) {
				tableName = tableName.substring(0, tableName.length() - 1);
			}
		}
		String className = toUpperCamelCase(tableName);
		if (pkgName.length() > 0) {
			return StringHelper.qualify(pkgName, className + "DAO");
		} else {
			return className;
		}
	}

	public String getTableIdentifierStrategyName(TableIdentifier arg0) {
		return "sequence";
	}

	public Properties getTableIdentifierProperties(TableIdentifier identifier) {

		Properties p = new Properties();
		p.put("sequence", "DR_S_CAMBIAR_POR_LA_CORRECTA");
		return p;
	}

	public String columnToPropertyName(TableIdentifier table, String column) {
		if (column.startsWith("X_")) {
			return "id";
		}
		return devuelveNombreAtributo(column);
	}

	private String devuelveNombreAtributo(String columna) {
		// Primera en minuscula y cada vez que se encuentre con un guion bajo pone la primera en mayuscula
		if ("".equals(columna.trim())) {
			return "";
		}
		// Nos ajustamos a la nomenclatura Java Bean de las propiedades
		if (columna.charAt(1) == '_') {
			columna = columna.replaceFirst("_", "");
		}
		String[] partes = columna.split("_");
		StringBuilder resultado = new StringBuilder(partes[0].toLowerCase());
		for (int i = 1; i < partes.length; i++) {
			// Lo ponemos todo en mayusculas
			partes[i] = partes[i].toUpperCase();
			// Cojemos la primera letra
			String primeraLetra = partes[i].substring(0, 1);
			// Pasamos el resto a minuscula
			String restoLetras = partes[i].substring(1, partes[i].length()).toLowerCase();
			// Guardamos
			resultado.append(primeraLetra);
			resultado.append(restoLetras);
		}
		return resultado.toString();
	}

	public String foreignKeyToCollectionName(String keyname, 
											 TableIdentifier fromTable, 
											 List fromColumns,
											 TableIdentifier referencedTable, 
											 List referencedColumns, 
											 boolean uniqueReference) {
		
		StringBuilder propertyName = new StringBuilder(Introspector.decapitalize(StringHelper.unqualify(getRoot().tableToClassName(fromTable))));
		propertyName = new StringBuffer(pluralize(propertyName.toString()));
		if (!uniqueReference) {
			if (fromColumns != null && fromColumns.size() == 1) {
				String columnName = ((Column) fromColumns.get(0)).getName();
				propertyName.append(propertyName);
				propertyName.append("For");
				propertyName.append(toUpperCamelCase(columnName));
			} else {
				propertyName.append(propertyName);
				propertyName.append("For");
				propertyName.append(toUpperCamelCase(keyname));
			}
		}
		return propertyName.toString();
	}

	public String foreignKeyToEntityName(String keyname, 
										 TableIdentifier fromTable, 
										 List fromColumnNames, 
										 TableIdentifier referencedTable, 
										 List referencedColumnNames, 
										 boolean uniqueReference) {
		
		StringBuilder propertyName = new StringBuilder(Introspector.decapitalize(StringHelper.unqualify(tableToClassName(referencedTable))));
		if (!uniqueReference) {
			if (fromColumnNames != null && fromColumnNames.size() == 1) {
				String columnName = ((Column) fromColumnNames.get(0)).getName();
				propertyName.append(propertyName);
				propertyName.append("By");
				propertyName.append(toUpperCamelCase(columnName));
			} else {
				propertyName.append(propertyName);
				propertyName.append("By");
				propertyName.append(toUpperCamelCase(keyname));
			}
		}
		
		return propertyName.toString();
	}

	protected String toUpperCamelCase(String s) {
		return ReverseEngineeringStrategyUtil.toUpperCamelCase(s);
	}

	protected String pluralize(String singular) {
		char last = singular.charAt(singular.length() - 1);
		switch (last) {
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			singular = singular + "s";
			break;
		case 's':
			break;
		default:
			singular = singular + "es";
			break;
		}
		return singular;
	}

	protected ReverseEngineeringStrategy getRoot() {
		return settings.getRootStrategy();
	}
}

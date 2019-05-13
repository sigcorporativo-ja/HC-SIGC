package es.juntadeandalucia.sigc.hc.core.configuration;

/**
 * WS Paths definition
 * 
 * @author Guadaltel S.A
 *
 */
public class Paths {
	
	public static final String FIND_ALL = "/list";

	public static final String FIND_ALL_PAGINATED = "/paginated";

	public static final String FIND_BY_ID = "/get/";

	public static final String CREATE = "/create";

	public static final String DELETE = "/delete";

	public static final String UPDATE = "/update";


	// Implicit constructor
	private Paths() {
		// empty constructor
	}

	/**
	 * Paths to seeker
	 * @author Guadaltel S.A
	 *
	 */
	public static class Seeker {
		
		private Seeker() {
			
		}
		
		public static final String FIND_BY_BBOX = "/findByBbox/";
	}
	
	/**
	 * Paths to bbox
	 * @author Guadaltel S.A
	 *
	 */
	public static class Bbox {
		
		private Bbox() {
			
		}
		
		public static final String BBOX = "/bbox/";
		
		public static final String AREA = "/area/";
	}
	
	/**
	 * Paths to resources
	 * @author Guadaltel S.A
	 *
	 */
	public static class ResourceType {
		
		private ResourceType() {
			
		}
		
		public static final String LIST = "/list";
		public static final String GENERIC = "/generic";
		public static final String PAGINATED = "/paginated";
		public static final String FIND_BY_TYPE_AND_ID = "/findByTypeAndId";
		public static final String FIND_BY_ID = "/get";
	}
	
	/**
	 * Paths to audiences
	 * @author Guadaltel S.A
	 *
	 */
	public static class AudienceType {
		
		private AudienceType() {
			
		}
		
		public static final String LIST = "/list";
	}
	
	/**
	 * Paths to Characteristic
	 * @author Guadaltel S.A
	 *
	 */
	public static class Characteristic {
		
		private Characteristic() {
			
		}
		
		public static final String LIST = "/list";
	}
	
	/**
	 * Paths to segments
	 * @author Guadaltel S.A
	 *
	 */
	public static class Segment {
		
		private Segment () {
			
		}
		
		public static final String LIST = "/list";
	}
	
	/**
	 * Paths to territories
	 * @author Guadaltel S.A
	 *
	 */
	public static class Territory {
		
		private Territory() {
			
		}
		
		public static final String PROVINCE = "/provinces";
		public static final String TOWN = "/townships";
	}
	
	/**
	 * Paths to nearby items
	 * @author Guadaltel S.A
	 *
	 */
	public static class Near {
		
		private Near() {
			
		}
		
		public static final String NEAR = "/nearby";
	}
	
	/**
	 * Paths to solr
	 * @author Guadaltel S.A
	 *
	 */
	public static class Solr {
		
		private Solr() {
			
		}
		
		public static final String RESOURCE = "/resource";
		
		public static final String SUGGEST = "/suggest";
	}
	
	/**
	 * 
	 * Paths to RESOURCES
	 * @author Guadaltel S.A
	 *
	 */
	public static class Resource {
		
		private Resource() {
			
		}
		
		public static final String RELATED = "/related";

	}
}
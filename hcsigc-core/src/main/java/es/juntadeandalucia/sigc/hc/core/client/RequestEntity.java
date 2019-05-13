package es.juntadeandalucia.sigc.hc.core.client;

/**
 * ws entities names
 *
 * @author Guadaltel S.A
 *
 */
public enum RequestEntity {
	SEEKER("seeker"),
	BBOX("bbox"),
	RESOURCE("resource"),
	RESOURCE_TYPE("resource_type"),
	TIPRECALOJAM("accommodation"),
	AUDICENCE_TYPE("audience_type"),
	SEGMENT("segment"),
	TERRITORY("territory"),
	TIPRECRESTAUR("restaurant"),
	TIPRECEVENTO("event"),
	TIPRECPLAYA("beach"),
	TIPRECGOLF("golf"),
	TIPRECFLAMENCO("flamenco_place"),
	TIPRECSALUDBELLEZA("health_and_beauty"),
	TIPRECTRANSPORTE("transport"),
	TIPRECSERVINF("tourist_info"),
	TIPRECAREANATURAL("nature_area"),
	TIPRECRUTATURIS("touristic_route"),
	TIPRECCOMPRA("shop"),
	TIPRECPARQUESOCIO("leisure"),
	TIPRECOCIONOCTURNO("nightlife"),
	TIPRECVISITA("visit"),
	TIPRECOFERTA("offer"),
	TIPRECCONVCONG("convention"),
	TIPRECAGEVIA("travel_agency"),
	TIPRECOTRCOM("other_marketer"),
	SPA("spa"),
	TIPRECESCESP("spanish_language_school"),
	TIPRECACTGUI("guided_activity_company"),
	TIPRECDEPORTE("active_tourism"),
	NATURAL_LANGUAGE("natural_language"),
	SUGGEST("suggest"),
	CHARACTERISTIC("characteristic"),
	SEASON("season"),
	GEOCODE("geocode");

	private String val;

	private RequestEntity(String val) {
		this.val = val;
	}

	/**
	 * Get the ws entity name
	 *
	 * @return
	 */
	public String value() {
		return this.val;
	}

	/**
	 * Get an instance from string value
	 *
	 * @param value
	 * @return
	 */
	public static RequestEntity fromValue(String value) {
		RequestEntity result = null;
		for (RequestEntity entity : values()) {
			if (entity.value().equals(value)) {
				result = entity;
				break;
			}
		}
		return result;
	}

	/**
	 * Get the ws entity path
	 *
	 * @return
	 */
	public String path() {
		return this.val;
	}
}

package es.juntadeandalucia.sigc.hc.rest.dto;

public class AuthTokenDTO {

	private String authToken;
	private String refreshToken;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}

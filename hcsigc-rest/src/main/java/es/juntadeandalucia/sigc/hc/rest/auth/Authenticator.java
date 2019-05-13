package es.juntadeandalucia.sigc.hc.rest.auth;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.LoginException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;
import org.picketlink.credential.DefaultLoginCredentials;

import es.juntadeandalucia.sigc.hc.core.bo.PermissionBO;
import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import es.juntadeandalucia.sigc.hc.rest.dto.AuthTokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@ApplicationScoped
public class Authenticator implements Serializable {

	private static final long serialVersionUID = 334176711962186830L;

	private static final Log LOG = LogFactory.getLog(Authenticator.class);

	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Inject
	private Identity identity;

	@Inject
	private DefaultLoginCredentials credentials;

	@Inject
	private JwtTokenAuthenticator jwtTokenAuthenticator;

	/**
	 * Metodo Login
	 * 
	 * @param username
	 * @param password
	 * @return Token JWT generado
	 * @throws LoginException
	 */
	public AuthTokenDTO login(String username, String password) throws LoginException {
		AuthTokenDTO response;
		
		if (identity.isLoggedIn()) {
			throw new LoginException("User already logged");
		}
		else {
			credentials.setUserId(username);
			credentials.setPassword(password);
			AuthenticationResult result = identity.login();
			if (result == AuthenticationResult.SUCCESS) {
				UserBO user = (UserBO) identity.getAccount();
				response = new AuthTokenDTO();
				response.setAuthToken(jwtTokenAuthenticator.createJwtAuthToken(user));
				response.setRefreshToken(jwtTokenAuthenticator.createJwtRefreshToken(user));
			}
			else {
				throw new LoginException("Wrong user and/or password");
			}
		}
		return response;
	}

	/**
	 * Metodo logout. No hace nada ya que no hay persistencia de tokens. El cliente
	 * debe encargarse de eliminar su token.
	 * 
	 * @param token
	 */
	public void logout(String token) {
		if (identity.isLoggedIn()) {
			identity.logout();
		}
	}

	/**
	 * Metodo refresh. Valida el refreshToken y emite un nuevo authToken.
	 * 
	 * @param refreshToken
	 */
	public AuthTokenDTO refresh(String refreshToken) throws AccountExpiredException {
		if (isTokenValid(refreshToken)) {
			UserBO user = getUserFromToken(refreshToken);
			if (user != null) {
				AuthTokenDTO response = new AuthTokenDTO();
				response.setAuthToken(jwtTokenAuthenticator.createJwtAuthToken(user));
				response.setRefreshToken(refreshToken);
				return response;
			}
		}

		throw new AccountExpiredException("Refresh token is invalid");
	}

	/**
	 * The method that pre-validates if the client which invokes the REST API is
	 * from a authorized and authenticated source.
	 * 
	 * @param token The authorization token generated after login
	 * @return TRUE for acceptance and FALSE for denied.
	 */
	public boolean isTokenValid(String token) {
		boolean res = false;
		try {
			res = jwtTokenAuthenticator.parseJwtToken(token).getExpiration().after(Calendar.getInstance().getTime());
		} catch (ExpiredJwtException e) {
			res = false;
		} catch (Exception e) {
			LOG.error("Error decoding JWT token", e);
			res = false;
		}

		return res;
	}

	/**
	 * Se recupera el identificador a partir del authToken
	 */
	public String getUserId(String token) {
		if (isTokenValid(token)) {
			return (String) jwtTokenAuthenticator.parseJwtToken(token).get("id");
		}

		return null;
	}

	/**
	 * Se extrae el token de la cabecera authentication
	 * 
	 * @param headers Cabeceras de la peticion
	 * @return Token de identificacion
	 */
	public String extractToken(MultivaluedMap<String, String> headers) {
		String token = null;

		List<String> authTokenList = headers.get(HttpHeaders.AUTHORIZATION);
		if (authTokenList != null && !authTokenList.isEmpty()) {
			token = StringUtils.substringAfter(authTokenList.get(0), AUTHENTICATION_SCHEME).trim();
		}

		return token;
	}

	/**
	 * Se extrae el token de la cabecera authentication
	 * 
	 * @param headers Cabeceras de la peticion
	 * @return User usuario logeado
	 */
	public UserBO getUser(MultivaluedMap<String, String> headers) {
		UserBO user = null;

		String token = extractToken(headers);
		if (token != null) {
			user = getUserFromToken(token);
		}

		return user;
	}

	/**
	 * Construye un objeto user a partir de la informacion que hayamos guardado en
	 * el token ¡No debemos guardar informacion sensible!
	 * 
	 * @param token
	 * @return User
	 */
	@SuppressWarnings("unchecked")
	public UserBO getUserFromToken(String token) {
		UserBO user = null;

		if (isTokenValid(token)) {
			Claims claims = jwtTokenAuthenticator.parseJwtToken(token);
			user = new UserBO();
			user.setId(getUserId(token));
			user.setName(claims.get("name", String.class));
			user.setSurname(claims.get("surname", String.class));
			user.setMail(claims.get("email", String.class));
			List<Map<String, Object>> claimPerms = (List<Map<String, Object>>) claims.get("permissions");
			List<PermissionBO> permissions = claimPerms.stream().map(cp -> {
				Integer id = (Integer) cp.get("id");
				String code = (String) cp.get("code");
				PermissionBO pbo = new PermissionBO();
				pbo.setId(id.longValue());
				pbo.setCode(code);
				return pbo;
			}).collect(Collectors.toList());
			user.setPermissions(permissions);
		}

		return user;
	}
}

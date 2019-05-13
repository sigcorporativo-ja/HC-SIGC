package es.juntadeandalucia.sigc.hc.rest.auth;

import java.io.Serializable;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.juntadeandalucia.sigc.hc.core.bo.UserBO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

@ApplicationScoped
public class JwtTokenAuthenticator implements Serializable {

	private static final long serialVersionUID = -7689224641584850857L;

	private static final Log LOG = LogFactory.getLog(JwtTokenAuthenticator.class);

	private static final String TOKEN_TYPE = "token_type";
	private static final String TOKEN_TYPE_AUTH = "auth_token";
	private static final String TOKEN_TYPE_REFRESH = "refresh_token";

	// Token issuer
	private String tokenIssuer;
	// Number of minutes during which our jwt will be valid
	private int authTokenExpirationMinutes;
	// Number of days during which our jwt refresh token will be valid
	private int refreshTokenExpirationMinutes;
	// The JWT signature algorithm we will be using to sign the token
	private SignatureAlgorithm signatureAlgorithm;
	// The key to sign JWT tokens
	private Key signingKey;

	@PostConstruct
	public void init() {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("jwt.properties");

			tokenIssuer = config.getString("JWT.TOKEN_ISSUER", "rest-issuer");
			authTokenExpirationMinutes = config.getInt("JWT.AUTH_TOKEN_EXPIRATION_MINUTES", 15);
			refreshTokenExpirationMinutes = config.getInt("JWT.REFRESH_TOKEN_EXPIRATION_HOURS", 2);
			signatureAlgorithm = SignatureAlgorithm.forName(config.getString("JWT.SIGNATURE_ALGORITHM", "HS512"));

			byte[] signingKeySecretBytes;
			String signingKeyString = config.getString("JWT.SIGNING_KEY", "GENERATE");
			if ("GENERATE".equals(signingKeyString)) {
				signingKeySecretBytes = MacProvider.generateKey(signatureAlgorithm).getEncoded();
			} else {
				signingKeySecretBytes = DatatypeConverter.parseBase64Binary(signingKeyString);
			}

			signingKey = new SecretKeySpec(signingKeySecretBytes, signatureAlgorithm.getJcaName());

		} catch (ConfigurationException e) {
			LOG.error("Error while initializing JwtTokenService", e);
		}
	}

	public String createJwtAuthToken(UserBO user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(TOKEN_TYPE, TOKEN_TYPE_AUTH);
		claims.put("id", user.getId());
		claims.put("username", user.getUsername());
		claims.put("name", user.getName());
		claims.put("surname1", user.getSurname());
		claims.put("email", user.getMail());
		claims.put("permissions", user.getPermissions());

		Date currentTime = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentTime);
		c.add(Calendar.MINUTE, authTokenExpirationMinutes);
		Date expiration = c.getTime();

		return Jwts.builder().setIssuer(tokenIssuer).setSubject(user.getId()).setClaims(claims)
				.setIssuedAt(currentTime).setNotBefore(currentTime).setExpiration(expiration)
				.signWith(signatureAlgorithm, signingKey).compact();
	}

	public String createJwtRefreshToken(UserBO user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(TOKEN_TYPE, TOKEN_TYPE_REFRESH);
		claims.put("username", user.getUsername());
		claims.put("name", user.getName());
		claims.put("surname1", user.getSurname());
		claims.put("email", user.getMail());
		claims.put("permissions", user.getPermissions());

		Date currentTime = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentTime);
		c.add(Calendar.HOUR_OF_DAY, refreshTokenExpirationMinutes);
		Date expiration = c.getTime();

		return Jwts.builder().setId(UUID.randomUUID().toString()).setIssuer(tokenIssuer).setSubject(user.getUsername())
				.setClaims(claims).setIssuedAt(currentTime).setNotBefore(currentTime).setExpiration(expiration)
				.signWith(signatureAlgorithm, signingKey).compact();
	}

	public Claims parseJwtToken(String authToken) throws ExpiredJwtException, SignatureException {
		return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(authToken).getBody();
	}
}

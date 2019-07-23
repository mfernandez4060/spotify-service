package spotify.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ResponseStatusException;

import spotify.service.TokenService;
import spotify.service.dto.TokenResponse;
import spotify.service.exceptions.BadClientIdException;
import spotify.service.exceptions.BadClientSecretException;
import spotify.service.util.ClientHttpRestApi;
import spotify.service.util.Commons;

/**
 * 
 * @author marfernandez 
 * AuthorizationServiceImpl Implementación de la interfaz AuthorizationService
 *
 */
@Component
public class AuthorizationServiceImpl implements AuthorizationService {
	static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	
	@Autowired
	TokenService tokenService;

	@Autowired
	Commons common;

	@Autowired
	ClientHttpRestApi clientHttpRestApi;

	@Value("${Service.urlTokenService}")
	private String urlTokenService;

	@Value("${Service.clientId}")
	private String serviceClientId;

	@Value("${Service.clientSecret}")
	private String serviceClientSecret;

	@Value("${Service.urlBaseSeervice}")
	private String serviceUrlBaseSeervice;

	/**
	 * getAuthorizationBasic 
	 * Genera un encode en Base64 con clientId y clientSecret
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return String
	 */
	String getAuthorizationBasic(String clientId, String clientSecret) {
		if (!common.checkValidClientId(clientId)) {
			throw new BadClientIdException(clientId);
		}

		if (!common.checkValidClientSecret(clientSecret)) {
			throw new BadClientSecretException(clientSecret);
		}

		String auth = clientId + ":" + clientSecret;

		return Base64Utils.encodeToString(auth.getBytes());
	}

	/**
	 * getTokenFromExternalService
	 * Obtiene un token para acceder al servicio externo
	 * 
	 * @return String
	 */
	@Override
	public TokenResponse getTokenFromExternalService() {
		try {
			String encodedAuthorization = getAuthorizationBasic(serviceClientId, serviceClientSecret);
			TokenResponse response = clientHttpRestApi.getToken(urlTokenService, encodedAuthorization);

			return response;
		} catch (ResponseStatusException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error in request");
		}
	}
}

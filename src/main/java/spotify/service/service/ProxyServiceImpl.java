package spotify.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import spotify.service.TokenService;
import spotify.service.dto.TokenResponse;
import spotify.service.exceptions.BackendServiceException;
import spotify.service.exceptions.BadUrlRequestException;
import spotify.service.exceptions.CustomException;
import spotify.service.util.ClientHttpRestApi;
import spotify.service.util.Commons;

/**
 * 
 * @author marfernandez
 * ProxyServiceImpl implementación de la interfaz de ProxyService
 * Gestiona peticiones a servicio externos 
 */
@Component
public class ProxyServiceImpl implements ProxyService {
	static Logger logger = LoggerFactory.getLogger(ProxyServiceImpl.class);
	@Autowired
	TokenService tokenService;
	
	@Autowired
	AuthorizationService authorizationService;
	
	@Autowired
	Commons common;

	@Autowired
	ClientHttpRestApi clientHttpRestApi;

	@Value("${Service.urlBaseSeervice}")
	private String serviceUrlBaseSeervice;

	/***
	 * proxyAction
	 * Ejecuta la urlRequest en el servicio externo
	 * @return String
	 */
	@Override
	public String proxyAction(String urlRequest) {
		String response=null;
		try {
			if (urlRequest == null) {
				throw new BadUrlRequestException("UrlRequest con null values");
			}
			
			if (tokenService.getToken() == null) {
				getAndSaveToken();
			}

			String urlAction = serviceUrlBaseSeervice + common.decodeActionUrl(urlRequest);

			try {
				response = executeProxyGetAction(urlAction);
			} catch (Exception e) {
				getAndSaveToken();
				response = executeProxyGetAction(urlAction);
			}
			
			return response;
		} catch (CustomException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BackendServiceException("Error in request " + urlRequest);
		}
	}

	private String executeProxyGetAction(String urlAction) {
		String response;
		response = clientHttpRestApi.proxyGetAction(urlAction, tokenService.getToken());
		return response;
	}

	private void getAndSaveToken() {
		TokenResponse token = authorizationService.getTokenFromExternalService();
		tokenService.setToken(token.getAccessToken());
	}

}

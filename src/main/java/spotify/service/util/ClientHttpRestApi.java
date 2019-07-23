package spotify.service.util;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import spotify.service.dto.TokenResponse;
import spotify.service.exceptions.BackendServiceException;

/**
 * 
 * @author marfernandez 
 * Implementación de cliente restful para
 */
@Component
public class ClientHttpRestApi {

	public TokenResponse getToken(String urlService, String encodedAuthorization) {

		return sendToken(urlService, encodedAuthorization);
	}

	@Autowired
	private RestTemplate appRestClient;

	/**
	 * Consume servicio restful para obtener token de acceso
	 * 
	 * @param urlService
	 * @param token
	 * @return TokenResponse conteniendo token válido para acceso a servicios
	 */
	private TokenResponse sendToken(String urlService, String token) {
		try {
			System.setProperty("http.proxyHost", "proxy.jus.gov.ar");
			System.setProperty("http.proxyPort", "8080");
			System.setProperty("https.proxyHost", "proxy.jus.gov.ar");
			System.setProperty("https.proxyPort", "8080");

			URI url = new URI(urlService);

			HttpEntity<MultiValueMap<String, String>> request = getHeaderToken(token);

			ResponseEntity<String> response = appRestClient.exchange(url, HttpMethod.POST, request, String.class);

			if (!response.getStatusCode().equals(HttpStatus.OK)) {
				throw new BackendServiceException("Error al obtener token");
			}
			return new TokenResponse(response.getBody());
		} catch (Exception e) {
			throw new BackendServiceException("Error al peticionar token " + e.getMessage());
		}
	}

	/**
	 * Setea header en request http token
	 * 
	 * @param token
	 * @return HttpEntity<MultiValueMap<String, String> con headers configurados
	 */
	public HttpEntity<MultiValueMap<String, String>> getHeaderToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + token);
		headers.add("Content-Type", "application/json; charset=utf8");
		headers.add("Accept", "application/json");
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "client_credentials");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		return request;
	}
	
	/**
	 * Setea header en request http token
	 * 
	 * @param token
	 * @return HttpEntity<MultiValueMap<String, String> con headers configurados
	 */
	public HttpEntity<MultiValueMap<String, String>> getHeaderGetAction(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.add("Accept", "application/json");
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		return request;
	}

	public String proxyGetAction(String urlRequest, String token) {
		try {
			System.setProperty("http.proxyHost", "proxy.jus.gov.ar");
			System.setProperty("http.proxyPort", "8080");
			System.setProperty("https.proxyHost", "proxy.jus.gov.ar");
			System.setProperty("https.proxyPort", "8080");

			URI url = new URI(urlRequest);

			HttpEntity<MultiValueMap<String, String>> request = getHeaderGetAction(token);

			ResponseEntity<String> response = appRestClient.exchange(url, HttpMethod.GET, request, String.class);

			if (!response.getStatusCode().equals(HttpStatus.OK)) {
				throw new BackendServiceException("Error al procesar la url request");
			}
			return response.getBody();
		} catch (Exception e) {
			throw new BackendServiceException("Error al procesar la url request " + e.getMessage());
		}
	}
}

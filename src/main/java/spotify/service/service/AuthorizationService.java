package spotify.service.service;

import spotify.service.dto.TokenResponse;
/***
 * 
 * @author marfernandez
 * AuthorizationService Interfaz de servicio de autorización
 */
public interface AuthorizationService {

	/**
	 * Retorna un token de acceso para acceder a servicios externos 
	 * 
	 * @return un TokenResponse
	 */
	TokenResponse getTokenFromExternalService();

}
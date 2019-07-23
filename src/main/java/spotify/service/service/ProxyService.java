package spotify.service.service;

/***
 * 
 * @author marfernandez
 * ProxyService interfaz de servicio de proxy
 */
public interface ProxyService {

	/**
	 * Proxy para operaciones get
	 * 
	 * @param urlTokenService
	 * @return un String con json response
	 */
	String proxyAction(String urlRequest);

}
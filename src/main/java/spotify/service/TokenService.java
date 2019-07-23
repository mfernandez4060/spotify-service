package spotify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @author marfernandez
 * Persiste en forma temporal el valor del token de servicio
 */
@Service
public class TokenService {
	private static final String TOKEN_SERVICE = "token-service";
	@Autowired
	private WebApplicationContext appContext;

	public void setToken(String value) {
	     appContext.getServletContext().setAttribute(TOKEN_SERVICE, value);
	  }
	
	public String getToken() {
	     return (String) appContext.getServletContext().getAttribute(TOKEN_SERVICE);
	  }
}

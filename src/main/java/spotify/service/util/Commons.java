package spotify.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import spotify.service.exceptions.BadUrlRequestException;
/**
 * 
 * @author marfernandez 
 * Funciones varias de validación de strings
 */
@Component
public class Commons {

	/**
	 * Valida clientId
	 * 
	 * @param clientId
	 * @return
	 */
	public boolean checkValidClientId(String clientId) {
		return checkhexadecimal(clientId);
	}

	/**
	 * Valida clientSecret
	 * 
	 * @param clientSecret
	 * @return
	 */
	public boolean checkValidClientSecret(String clientSecret) {
		return checkhexadecimal(clientSecret);
	}

	/**
	 * Valída hexadecimal
	 * 
	 * @param clientId
	 * @return
	 */
	private boolean checkhexadecimal(String clientId) {
		String pattern = "[0-9a-fA-F]+";
		Pattern r = Pattern.compile(pattern);
		if (clientId == null)
			return false;

		Matcher m = r.matcher(clientId);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public String decodeActionUrl(String urlRequest) {
		String response = null;
		if (urlRequest == null)
			throw new BadUrlRequestException("null value");
		try {
			response = new String(Base64.decodeBase64(urlRequest));
			if (response == null || response.length() == 0)
				throw new BadUrlRequestException("urlRequest inválida " + urlRequest);
		} catch (Exception e) {
			throw new BadUrlRequestException("urlRequest inválida " + urlRequest);
		}
		return response;
	}
}

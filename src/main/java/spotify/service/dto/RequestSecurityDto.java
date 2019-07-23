package spotify.service.dto;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
/**
 * 
 * @author marfernandez
 * RequestSecurityDto Request para el servicio de token
 */
@Component
public class RequestSecurityDto {
	private String clientId;
	private String clientSecret;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public String toString() {
		return "RequestSecurityDto [clientId=" + clientId + ", clientSecret=" + clientSecret + "]";
	}

}

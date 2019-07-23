package spotify.service.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * 
 * @author marfernandez
 * TokenResponse Respuesta del servico de token
 */
public class TokenResponse {
	@SerializedName("access_token")
	@Expose
	public String accessToken;
	@SerializedName("token_type")
	@Expose
	public String tokenType;
	@SerializedName("expires_in")
	@Expose
	public Integer expiresIn;
	@SerializedName("scope")
	@Expose
	public String scope;
	
	public TokenResponse(String json) {
		Gson gson = new Gson();
		//{"access_token":"BQBW91RMWMXDsP7cQpex-R_sGI6LuGHt5b9un7LEGE1FMxEPbHGteDCw4gMlYUNe8ka8-Xny2VqRahU4ung","token_type":"Bearer","expires_in":3600,"scope":""}
		TokenResponse t = gson.fromJson(json, TokenResponse.class);
		this.accessToken = t.accessToken;
		this.expiresIn = t.expiresIn;
		this.scope =t.scope;
		this.tokenType = t.tokenType;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
}
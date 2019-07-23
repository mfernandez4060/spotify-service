package spotify.service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import spotify.service.dto.TokenResponse;
import spotify.service.util.ClientHttpRestApi;
import spotify.service.util.Commons;

@SpringBootTest
public class AuthorizationServiceImplTest {
	private static final String VALID_ENCODED = "ZDFhOTA2YjFhZmZjNGIyZDliZGM5MDgxNmNiNjJlNmU6NjVkY2ZkZWQ5MGJlNDcxYTk0NjU1YmQwODM0N2UzMTI=";
	private static final String VALID_CLIENT_SECRET = "65dcfded90be471a94655bd08347e312";
	private static final String VALID_CLIENT_ID = "d1a906b1affc4b2d9bdc90816cb62e6e";
	private static final String INVALID_CLIENT_SECRET = "x65dcfded90be471a94655bd08347e312";
	private static final String INVALID_CLIENT_ID = "xd1a906b1affc4b2d9bdc90816cb62e6e";
	private static final String VALID_URL_TOKEN_SERVICE = "https://accounts.spotify.com/api/token";	
	private static final TokenResponse VALID_TOKEN_RESPONSE = new TokenResponse(
			"{\"access_token\":\"BQDCbFMj5OwoIuek37Tn1K5cna8KbUN1P3l-ehES98BFosuOuYOhn9Wd4fcc5OAGTQZGDoj4PoSbmnHj56M\",\"token_type\":\"Bearer\",\"expires_in\":3600,\"scope\":\"\"}");

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(sut, "urlTokenService", "https://accounts.spotify.com/api/token");
		ReflectionTestUtils.setField(sut, "serviceClientId", "d1a906b1affc4b2d9bdc90816cb62e6e");
		ReflectionTestUtils.setField(sut, "serviceClientSecret", "65dcfded90be471a94655bd08347e312");		
		ReflectionTestUtils.setField(sut, "serviceUrlBaseSeervice", "https://api.spotify.com/v1/");
	}

	@AfterEach
	public void tearDown() {
	}

	@Mock
	private Commons common;
	@Mock
	private ClientHttpRestApi clientHttpRestApi;

	@InjectMocks
	private AuthorizationServiceImpl sut;

	@Value("${Service.urlTokenService}")
	private String urlService;

	@Test
	void getTokenFromExternalService_withValidClientIDAndClientSecret_returnTrueInCheckValid() {
		Mockito.when(common.checkValidClientId(VALID_CLIENT_ID)).thenReturn(true);
		Mockito.when(common.checkValidClientSecret(VALID_CLIENT_SECRET)).thenReturn(true);
		Mockito.when(clientHttpRestApi.getToken(VALID_URL_TOKEN_SERVICE, VALID_ENCODED)).thenReturn(VALID_TOKEN_RESPONSE);

		TokenResponse token = sut.getTokenFromExternalService();

		verify(common, times(1)).checkValidClientId(VALID_CLIENT_ID);
		verify(common, times(1)).checkValidClientSecret(VALID_CLIENT_SECRET);

		assertEquals(VALID_TOKEN_RESPONSE, token);

	}

	@Test
	void getAuthorizationBasic_withValidClientIDAndClientSecret_returnTrueInCheckValid() {
		Mockito.when(common.checkValidClientId(VALID_CLIENT_ID)).thenReturn(true);
		Mockito.when(common.checkValidClientSecret(VALID_CLIENT_SECRET)).thenReturn(true);

		sut.getAuthorizationBasic(VALID_CLIENT_ID, VALID_CLIENT_SECRET);

		verify(common, times(1)).checkValidClientId(VALID_CLIENT_ID);
		verify(common, times(1)).checkValidClientSecret(VALID_CLIENT_SECRET);
	}

	@Test
	void getAuthorizationBasic_withNullClientIDAndInvalidclientSecret_returnValidEncode() {
		Mockito.when(common.checkValidClientId(null)).thenReturn(false);
		Mockito.when(common.checkValidClientSecret(INVALID_CLIENT_SECRET)).thenReturn(false);

		Exception thrown = assertThrows(ResponseStatusException.class,
				() -> sut.getAuthorizationBasic(INVALID_CLIENT_ID, INVALID_CLIENT_SECRET));

		assertEquals(thrown.getClass(), ResponseStatusException.class);
	}

	@Test
	void getAuthorizationBasic_withInvalidClientIDAndInvalidClientSecret_returnValidEncode() {
		Mockito.when(common.checkValidClientId(INVALID_CLIENT_ID)).thenReturn(false);
		Mockito.when(common.checkValidClientSecret(INVALID_CLIENT_SECRET)).thenReturn(false);

		Exception thrown = assertThrows(ResponseStatusException.class,
				() -> sut.getAuthorizationBasic(INVALID_CLIENT_ID, INVALID_CLIENT_SECRET));

		assertEquals(thrown.getClass(), ResponseStatusException.class);
	}

	@Test
	void getAuthorizationBasic_withValidClientIDAndInvalidClientSecret_returnValidEncode() {
		Mockito.when(common.checkValidClientId(VALID_CLIENT_ID)).thenReturn(true);
		Mockito.when(common.checkValidClientSecret(INVALID_CLIENT_SECRET)).thenReturn(false);

		Exception thrown = assertThrows(ResponseStatusException.class,
				() -> sut.getAuthorizationBasic(VALID_CLIENT_ID, INVALID_CLIENT_SECRET));

		assertEquals(thrown.getClass(), ResponseStatusException.class);
	}

	@Test
	void getAuthorizationBasic_withInvalidClientIDAndValidclientSecret_returnValidEncode() {
		Mockito.when(common.checkValidClientId(INVALID_CLIENT_ID)).thenReturn(false);
		Mockito.when(common.checkValidClientSecret(VALID_CLIENT_SECRET)).thenReturn(true);

		Exception thrown = assertThrows(ResponseStatusException.class,
				() -> sut.getAuthorizationBasic(INVALID_CLIENT_ID, VALID_CLIENT_SECRET));

		assertEquals(thrown.getClass(), ResponseStatusException.class);
	}

	@Test
	void getAuthorizationBasic_withValidClientIDAndClientSecret_returnValidEncoded() {
		Mockito.when(common.checkValidClientId(VALID_CLIENT_ID)).thenReturn(true);
		Mockito.when(common.checkValidClientSecret(VALID_CLIENT_SECRET)).thenReturn(true);

		String authorizationBasic = sut.getAuthorizationBasic(VALID_CLIENT_ID, VALID_CLIENT_SECRET);

		verify(common, times(1)).checkValidClientId(VALID_CLIENT_ID);
		verify(common, times(1)).checkValidClientSecret(VALID_CLIENT_SECRET);

		assertEquals(VALID_ENCODED, authorizationBasic);
	}

	@Test
	void getAuthorizationBasic_withValidClientIDAndClientSecret_returnInvalidEncoded() {
		Mockito.when(common.checkValidClientId(VALID_CLIENT_ID)).thenReturn(true);
		Mockito.when(common.checkValidClientSecret(VALID_CLIENT_SECRET)).thenReturn(true);

		String authorizationBasic = sut.getAuthorizationBasic(VALID_CLIENT_ID, VALID_CLIENT_SECRET);

		verify(common, times(1)).checkValidClientId(VALID_CLIENT_ID);
		verify(common, times(1)).checkValidClientSecret(VALID_CLIENT_SECRET);

		assertEquals(VALID_ENCODED, authorizationBasic);
	}
}

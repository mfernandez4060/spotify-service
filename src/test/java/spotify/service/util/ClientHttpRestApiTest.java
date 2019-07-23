package spotify.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import spotify.service.dto.TokenResponse;
import spotify.service.exceptions.BackendServiceException;
import spotify.service.exceptions.BadUrlRequestException;

@TestPropertySource(properties = {
		"Service.urlTokenService=https://accounts.spotify.com/api/token",})
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ClientHttpRestApiTest {

	private static final String VALID_ENCODED_AUTHORIZATION = "ZDFhOTA2YjFhZmZjNGIyZDliZGM5MDgxNmNiNjJlNmU6NjVkY2ZkZWQ5MGJlNDcxYTk0NjU1YmQwODM0N2UzMTI=";
	private static final String INVALID_ENCODED_AUTHORIZATION = "ZDFhOTA2YjFhZmZjNGIyZDliZGM5MDgxNmNiNjJlNmU6NjVkY2ZkZWQ5MGJlNDcxYTk0NjU1YmQwODM0N2UzMTI=";

	private static final String VALID_BODY = "{\"access_token\":\"BQDCbFMj5OwoIuek37Tn1K5cna8KbUN1P3l-ehES98BFosuOuYOhn9Wd4fcc5OAGTQZGDoj4PoSbmnHj56M\",\"token_type\":\"Bearer\",\"expires_in\":3600,\"scope\":\"\"}";
	private static final String VALID_URL_REQUEST = "/bla/bla";

	private static final String VALID_TOKEN = "BQDCbFMj5OwoIuek37Tn1K5cna8KbUN1P3l-ehES98BFosuOuYOhn9Wd4fcc5OAGTQZGDoj4PoSbmnHj56M";

	@BeforeEach
	public void setUp() {
	}

	@AfterEach
	public void tearDown() {
	}

	@Mock
	private RestTemplate appRestClient;

	@Value("${Service.urlTokenService}")
	private String urlService;

	@InjectMocks
	private ClientHttpRestApi sut;

	@Test
	void getToken_withValidClientID_returnTrue() {
		ResponseEntity<String> response = new ResponseEntity<String>(VALID_BODY,
				HttpStatus.OK);

		Mockito.when(appRestClient.exchange(ArgumentMatchers.any(URI.class),
				ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())).thenReturn(response);

		TokenResponse token = sut.getToken(urlService,
				VALID_ENCODED_AUTHORIZATION);

		assertTrue(token.getAccessToken() != null);
		assertTrue(token.getAccessToken().length() > 20);
		assertEquals(VALID_TOKEN, token.getAccessToken());
	}

	@Test
	void getToken_withNull_throwException() {
		ResponseEntity<String> response = new ResponseEntity<String>(VALID_BODY,
				HttpStatus.BAD_REQUEST);

		Mockito.when(appRestClient.exchange(ArgumentMatchers.any(URI.class),
				ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())).thenReturn(response);

		Exception thrown = assertThrows(BackendServiceException.class,
				() -> sut.getToken(urlService, INVALID_ENCODED_AUTHORIZATION));

		assertEquals(thrown.getClass(), BackendServiceException.class);
	}

	@Test
	void getToken_withInvalidClientID_throwException() {
		ResponseEntity<String> response = new ResponseEntity<String>(VALID_BODY,
				HttpStatus.CONFLICT);

		Mockito.when(appRestClient.exchange(ArgumentMatchers.any(URI.class),
				ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())).thenReturn(response);

		Exception thrown = assertThrows(BackendServiceException.class,
				() -> sut.getToken(urlService, INVALID_ENCODED_AUTHORIZATION));

		assertEquals(thrown.getClass(), BackendServiceException.class);
	}

	@Test
	void proxyGetAction_withValidUrlRequestD_returnValidResult() {
		ResponseEntity<String> responseExcpected = new ResponseEntity<String>(VALID_BODY,
				HttpStatus.OK);

		Mockito.when(appRestClient.exchange(ArgumentMatchers.any(URI.class),
				ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())).thenReturn(responseExcpected);

		String response = sut.proxyGetAction(VALID_URL_REQUEST, VALID_TOKEN);

		assertEquals(responseExcpected.getBody(), response);
	}

	@Test
	void proxyGetAction_withNull_throwException() {
		ResponseEntity<String> response = new ResponseEntity<String>(VALID_BODY,
				HttpStatus.CONFLICT);

		Mockito.when(appRestClient.exchange(ArgumentMatchers.any(URI.class),
				ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())).thenReturn(response);

		Exception thrown = assertThrows(BackendServiceException.class,
				() ->  sut.proxyGetAction(null, VALID_TOKEN));

		assertEquals(thrown.getClass(), BackendServiceException.class);
	}

	@Test
	void proxyGetAction_withInvalidClientID_throwException() {
		ResponseEntity<String> response = new ResponseEntity<String>(VALID_BODY,
				HttpStatus.BAD_REQUEST);

		Mockito.when(appRestClient.exchange(ArgumentMatchers.any(URI.class),
				ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())).thenReturn(response);

		Exception thrown = assertThrows(BackendServiceException.class,
				() ->  sut.proxyGetAction(null, VALID_TOKEN));

		assertEquals(thrown.getClass(), BackendServiceException.class);
	}
}

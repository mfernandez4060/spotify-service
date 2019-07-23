package spotify.service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import spotify.service.TokenService;
import spotify.service.exceptions.BackendServiceException;
import spotify.service.exceptions.BadUrlRequestException;
import spotify.service.util.ClientHttpRestApi;
import spotify.service.util.Commons;

@SpringBootTest
public class ProxyServiceImplTest {
	private static final String VALID_TOKEN_STRING = "BQDCbFMj5OwoIuek37Tn1K5cna8KbUN1P3l-ehES98BFosuOuYOhn9Wd4fcc5OAGTQZGDoj4PoSbmnHj56M";
	private static final String VALID_ACTION_RESPONSE = "{service: \"Ok Response\"}";
	private static final String VALID_URL_RESQUEST = "/bla/bla";
	private static final String INVALID_URL_RESQUEST = "bla";
	private static final String VALID_BODY = "body";

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(sut, "serviceUrlBaseSeervice", "https://api.spotify.com/v1/");
	}

	@AfterEach
	public void tearDown() {
	}

	@Mock
	private TokenService tokenService;

	@Mock
	private ClientHttpRestApi clientHttpRestApi;
	
	@Mock
	private Commons common;
	
	@InjectMocks
	private ProxyServiceImpl sut;

	@Test
	void proxyAction_withValidUrlRequest_returnTrueInCheckValid() {
		Mockito.when(tokenService.getToken()).thenReturn(VALID_TOKEN_STRING);
		Mockito.when(clientHttpRestApi.proxyGetAction(Mockito.anyString(), Mockito.anyString())).thenReturn(VALID_ACTION_RESPONSE);
		Mockito.when(common.decodeActionUrl(Mockito.anyString())).thenReturn(VALID_URL_RESQUEST);
		
		String  response = sut.proxyAction(VALID_URL_RESQUEST);

		verify(clientHttpRestApi, times(1)).proxyGetAction(Mockito.anyString(), Mockito.anyString());
		verify(common, times(1)).decodeActionUrl(Mockito.anyString());

		assertEquals(VALID_ACTION_RESPONSE, response);
	}

	@Test
	void proxyAction_withNull_returnBadUrlRequestException() {
		Mockito.when(tokenService.getToken()).thenReturn(VALID_TOKEN_STRING);
		Mockito.when(clientHttpRestApi.proxyGetAction(Mockito.anyString(), Mockito.anyString())).thenReturn(VALID_ACTION_RESPONSE);
		Mockito.when(common.decodeActionUrl(Mockito.anyString())).thenReturn(VALID_URL_RESQUEST);
		
		Exception thrown = assertThrows(BadUrlRequestException.class,
				() -> sut.proxyAction(null));

		assertEquals(thrown.getClass(), BadUrlRequestException.class);
	}
	@Test
	void proxyAction_withInvalidUrlRequest_returnBadUrlRequestException() {
		Mockito.when(tokenService.getToken()).thenReturn(VALID_TOKEN_STRING);
		Mockito.when(clientHttpRestApi.proxyGetAction(Mockito.anyString(), Mockito.anyString())).thenThrow(new BackendServiceException("Error al procesar la url request"));
		Mockito.when(common.decodeActionUrl(Mockito.anyString())).thenReturn(VALID_URL_RESQUEST);
		
		Exception thrown = assertThrows(BackendServiceException.class,
				() -> sut.proxyAction(INVALID_URL_RESQUEST));

		assertEquals(thrown.getClass(), BackendServiceException.class);
	}	
}

package spotify.service.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import spotify.service.exceptions.BadUrlRequestException;
import spotify.service.service.ProxyService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "Service.urlTokenService=https://accounts.spotify.com/api/token", })
public class RestServiceTest {
	private static final String VALID_RESPONSE = "{\"data\":\"BQDCbFMj5OwoIuek37Tn1K5cna8KbUN1P3l-ehES98BFosuOuYOhn9Wd4fcc5OAGTQZGDoj4PoSbmnHj56M\"}";

	private static final String INVALID_PARAM = null;

	@BeforeEach
	void init() {
	}

	@AfterEach
	void tearDown() {
	}

	@LocalServerPort
	private int port;

	@Mock
	private ProxyService proxyService;

	@InjectMocks
	private RestService sut;

	public void proxyAction_whithValidParam_returnValidTokenResponse() throws Exception {
		Mockito.when(proxyService.proxyAction(ArgumentMatchers.any(String.class))).thenReturn(VALID_RESPONSE);
		String response = sut.proxyAction(ArgumentMatchers.any(String.class));

		Mockito.verify(proxyService, Mockito.times(1)).proxyAction(ArgumentMatchers.any(String.class));
		assertEquals(response, ArgumentMatchers.any(String.class));
	}

	@Test()
	public void proxyAction_whithInvalidValidParam_return409HttpCode() throws Exception {
		Mockito.when(proxyService.proxyAction(INVALID_PARAM)).thenThrow(new BadUrlRequestException("UrlRequest con null values"));
		Exception thrown = assertThrows(BadUrlRequestException.class, () -> sut.proxyAction(INVALID_PARAM));

		assertEquals(thrown.getClass(), BadUrlRequestException.class);
	}

}

package spotify.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import spotify.service.exceptions.BadUrlRequestException;

@SpringBootTest
public class CommonTest {
	private static final String INVALID_URL_RESQUEST_ENCODED = "!x";
	private static final String INVALID_CLIENT_SECRET = "";
	private static final String VALID_CLIENT_ID = "d1a906b1affc4b2d9bdc90816cb62e6e";
	private static final String VALID_URL_RESQUEST_ENCODED = new String(Base64.encodeBase64("/bla/bla".getBytes()));
	private static final String VALID_URL_RESQUEST_DECODED = "/bla/bla";

	public void setUp() {
	};

	public void tearDown() {
	};

	@InjectMocks
	private Commons sut;

	@Test
	void checkValidClientId_withValidClientID_returnTrue() {
		boolean retorno = sut.checkValidClientId(VALID_CLIENT_ID);

		assertTrue(retorno);
	}

	@Test
	void checkValidClientId_withNullCientID_returnFalse() {
		boolean retorno = sut.checkValidClientId(null);

		assertFalse(retorno);
	}

	@Test
	void checkValidClientId_withInvalidClientID_returnFalse() {
		boolean retorno = sut.checkValidClientId(INVALID_CLIENT_SECRET);

		assertFalse(retorno);
	}

	@Test
	void voidcheckValidClientSecret_withValidClientID_returnTrue() {
		boolean retorno = sut.checkValidClientSecret(VALID_CLIENT_ID);

		assertTrue(retorno);
	}

	@Test
	void checkValidClientSecret_withInvalidClientID_returnFalse() {
		boolean retorno = sut.checkValidClientSecret(INVALID_CLIENT_SECRET);

		assertFalse(retorno);
	}

	@Test
	void decodeActionUrl_withValidUrlRequest_returnValidDecode() {
		String retorno = sut.decodeActionUrl(VALID_URL_RESQUEST_ENCODED);

		assertEquals(VALID_URL_RESQUEST_DECODED, retorno);
	}

	@Test
	void decodeActionUrl_withNullUrlRequest_returnValidDecode() {
		Exception thrown = assertThrows(BadUrlRequestException.class, () -> sut.decodeActionUrl(null));

		assertEquals(thrown.getClass(), BadUrlRequestException.class);
	}
	
	@Test
	void decodeActionUrl_withInvalidUrlRequest_returnValidDecode() {
		Exception thrown = assertThrows(BadUrlRequestException.class, () -> sut.decodeActionUrl(INVALID_URL_RESQUEST_ENCODED));

		assertEquals(thrown.getClass(), BadUrlRequestException.class);
	}	
}

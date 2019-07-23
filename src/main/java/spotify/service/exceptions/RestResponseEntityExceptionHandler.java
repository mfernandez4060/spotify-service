package spotify.service.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import spotify.service.service.AuthorizationServiceImpl;

/***
 * 
 * @author marfernandez
 * RestResponseEntityExceptionHandler encargada de manejar los errores en la capa de servicios
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	
	
	@ExceptionHandler({ BadClientIdException.class })
	public ResponseEntity<Object> badClientIdException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ BadUrlRequestException.class })
	public ResponseEntity<Object> badUrlRequestException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ BackendServiceException.class })
	public ResponseEntity<Object> backendServiceException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler({ BadClientSecretException.class })
	public ResponseEntity<Object> BadClientSecretException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
}
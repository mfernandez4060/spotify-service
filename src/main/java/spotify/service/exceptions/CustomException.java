package spotify.service.exceptions;

/***
 *  
 * @author marfernandez
 * CustomException Clase base para errores
 */
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = -7976254375516760150L;

	public CustomException(String string) {
		super(string);
	}


}

package spotify.service.exceptions;
/***
 * 
 * @author marfernandez
 * BadClientIdException
 */
public class BadClientIdException extends CustomException {

	private static final long serialVersionUID = 7806088387159169700L;

	public BadClientIdException(String clientId) {
        super("Bad clientId [" + clientId + "]");
    }

}

package spotify.service.exceptions;
/***
 * 
 * @author marfernandez
 * BadClientIdException
 */
public class BadClientSecretException extends CustomException {

	private static final long serialVersionUID = 7806088387159169700L;

	public BadClientSecretException(String clientSecret) {
        super("Bad ClientSecret [" + clientSecret + "]");
    }

}

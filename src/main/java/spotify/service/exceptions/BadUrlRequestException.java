package spotify.service.exceptions;

/***
 * 
 * @author marfernandez
 * BadUrlRequestException 
 */
public class BadUrlRequestException extends CustomException {

	private static final long serialVersionUID = 7806088387159169700L;

	public BadUrlRequestException(String urlRequest) {
        super("Bad Url Request [" + urlRequest + "]");
    }

}

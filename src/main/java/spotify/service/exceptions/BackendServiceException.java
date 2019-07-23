package spotify.service.exceptions;

/***
 * 
 * @author marfernandez
 * BackendServiceException Errores internos del servicio
 */
public class BackendServiceException extends CustomException {
	private static final long serialVersionUID = 7806088387159169700L;

	public BackendServiceException(String error) {
        super("Error al ejecutar el servicio en backend Error: " + error);
    }

}

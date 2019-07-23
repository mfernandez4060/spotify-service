package spotify.service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import spotify.service.service.ProxyService;
/**
 * 
 * @author marfernandez
 * Servicio Restful que hacer de proxy a servicios externos para que puedan ser consumidos por la ui
 * RestService Interfaz web de servicios restful V1
 */
@RestController
@RequestMapping("/api/v1")
public class RestService {
	static Logger logger = LoggerFactory.getLogger(RestService.class);
	
	
	@Autowired
	private ProxyService proxyService;

	/**
	 * 
	 * @return Retorna la version activa, se puede usar como alive
	 */
    @ResponseBody
    @GetMapping("/version")
    public String getVersion() {
    	logger.info("/getVersion");
        return "{version: '1.0.0'}";
    }
    
    /**
     * @param requestSecurityDto
     * @return Retorna json de servicio invocado en backend
     */
    @ResponseBody
    @RequestMapping(value = "/proxy", method = RequestMethod.GET)
    public String proxyAction(@RequestParam(name="urlRequest") String urlRequest) {
    	logger.info("/proxy " + urlRequest);
    	String response = proxyService.proxyAction(urlRequest);
    	
        return response;
    }    
}

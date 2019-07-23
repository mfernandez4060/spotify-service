package spotify.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
/**
 * 
 * @author marfernandez
 * 
 * Startup del proyecto
 */
@SpringBootApplication
@ComponentScan
@Configuration
public class ApplicationUp {
	static Logger logger = LoggerFactory.getLogger(ApplicationUp.class);
	/*
	 * Bean que configura el cliente de servicios rest
	 */
	@Bean(name = "appRestClient")
	public RestTemplate getRestClient() {

	    RestTemplate  restClient = new RestTemplate(
	        new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

	    return restClient;
	}

	/*
	 * Startup
	 */
	public static void main(String[] args) {
		logger.info("Start application");
		SpringApplication.run(ApplicationUp.class, args);
	}
}
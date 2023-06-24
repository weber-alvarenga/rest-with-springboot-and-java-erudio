package br.com.erudio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
		// Via EXTENSION http://localhost:8080/person/v1.xml DEPRECATED on Sprint Boot 2.6
		// Via QueryParam http://localhost:8080/person/v1?mediaType=xml
		
		configurer.favorParameter(true)			// Aceita parâmetros
			.parameterName("mediaType")			// Nome do parâmetro esperado
			.ignoreAcceptHeader(true)			// Ignora parâmetos no header
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML);
	}

}

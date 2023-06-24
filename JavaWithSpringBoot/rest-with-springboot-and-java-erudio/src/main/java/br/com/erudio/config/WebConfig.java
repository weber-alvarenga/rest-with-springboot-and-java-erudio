package br.com.erudio.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.erudio.serialization.converter.YamlJackson2HttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private final static MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");
	

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		// https://www.baeldung.com/spring-mvc-content-negotiation-json-xml
		// Via EXTENSION http://localhost:8080/person/v1.xml DEPRECATED on Sprint Boot 2.6
		
		/*
		// Via QueryParam http://localhost:8080/person/v1?mediaType=xml
		
		configurer.favorParameter(true)			// Aceita parâmetros
			.parameterName("mediaType")			// Nome do parâmetro esperado
			.ignoreAcceptHeader(true)			// Ignora parâmetos no header
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML);
		*/
		
		// Via Header Param http://localhost:8080/person/v1
		// Adicionar Header "Accept" na solicitação com valores "application/xml" ou "application/json"
		
		configurer.favorParameter(false)		// Ignora parâmetros
			.ignoreAcceptHeader(false)			// Aceita parâmetos no header
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);

	}


	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

		converters.add(new YamlJackson2HttpMessageConverter());
		
	}

}

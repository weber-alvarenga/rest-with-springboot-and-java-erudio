package br.com.erudio.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.PersonVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import br.com.erudio.test.config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification requestSpecification;
	private static ObjectMapper objectMapper;
	
	private static PersonVO person;
	
	@LocalServerPort
	private Integer port;
	
	
	@BeforeAll
	public static void setup() {
		
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);	// ignora quando propriedades desconhecidas na mensagem recebida
																					// como os links do HATEOAS que não existem no PersonVO.
		
		person = new PersonVO();
		
	}
	
	
	@Test
	@Order(0)
	public void authorizaton() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialsVO credentials = new AccountCredentialsVO("leandro", "admin1234");
		
		String accessToken = given()
								.basePath("/auth/signin")
								.port(port)
								.contentType(TestConfig.CONTENT_TYPE_JSON)
								.body(credentials)
								.when()
								.post()
								.then()
									.statusCode(200)
										.extract()
										.body()
										.as(TokenVO.class)
										.getAccessToken();

		requestSpecification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/person/v1")
				.setPort(port)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
	}

	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		
		mockPerson();
/*		
		requestSpecification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
				.setBasePath("/person/v1")
				.setPort(port)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
*/		
		
		String content = given()
							.spec(requestSpecification)
								.contentType(TestConfig.CONTENT_TYPE_JSON)
								.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
								.body(person)
							.when()
							.post()
							.then()
								.statusCode(200)
							.extract()
								.body().asString();
		
		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		
		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());		
		
		Assertions.assertTrue(persistedPerson.getId() > 0);
		
		assertEquals(person.getFirstName(), persistedPerson.getFirstName());
		assertEquals(person.getLastName(), persistedPerson.getLastName());
		assertEquals(person.getAddress(), persistedPerson.getAddress());
		assertEquals(person.getGender(), persistedPerson.getGender());
		
		person = persistedPerson;		// seta o person para ser utilizado nos testes subsequentes.
		
	}

	
	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		
		mockPerson();
/*		
		requestSpecification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_SEMERU)
				.setBasePath("/person/v1")
				.setPort(port)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
*/		
		
		String content = given()
							.spec(requestSpecification)
								.contentType(TestConfig.CONTENT_TYPE_JSON)
								.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_SEMERU)
								.body(person)
							.when()
							.post()
							.then()
								.statusCode(403)
							.extract()
								.body().asString();
		

		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
		
	}

	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
/*		
		requestSpecification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
				.setBasePath("/person/v1")
				.setPort(port)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
*/		
		
		String content = given()
							.spec(requestSpecification)
								.contentType(TestConfig.CONTENT_TYPE_JSON)
								.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
								.pathParam("id", person.getId())
							.when()
							.get("{id}")
							.then()
								.statusCode(200)
							.extract()
								.body().asString();
		
		PersonVO resultPerson = objectMapper.readValue(content, PersonVO.class);
		
		assertNotNull(resultPerson);
		assertNotNull(resultPerson.getId());
		assertNotNull(resultPerson.getFirstName());
		assertNotNull(resultPerson.getLastName());
		assertNotNull(resultPerson.getAddress());
		assertNotNull(resultPerson.getGender());		
		
		Assertions.assertTrue(resultPerson.getId() > 0);
		
		assertEquals(person.getFirstName(), resultPerson.getFirstName());
		assertEquals(person.getLastName(), resultPerson.getLastName());
		assertEquals(person.getAddress(), resultPerson.getAddress());
		assertEquals(person.getGender(), resultPerson.getGender());
		
	}

	
	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
/*		
		requestSpecification = new RequestSpecBuilder()
				.addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_SEMERU)
				.setBasePath("/person/v1")
				.setPort(port)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
*/		
		
		String content = given()
							.spec(requestSpecification)
								.contentType(TestConfig.CONTENT_TYPE_JSON)
								.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_SEMERU)
								.pathParam("id", person.getId())
							.when()
							.get("{id}")
							.then()
								.statusCode(403)
							.extract()
								.body().asString();


		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
		
	}


	private void mockPerson() {

		person.setFirstName("Paul");
		person.setLastName("Atreides");
		person.setAddress("Caladan");
		person.setGender("Male");
		
	}

}

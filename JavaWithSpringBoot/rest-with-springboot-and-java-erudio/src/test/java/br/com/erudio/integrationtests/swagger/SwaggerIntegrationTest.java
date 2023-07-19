package br.com.erudio.integrationtests.swagger;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.test.config.TestConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8888"})
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void shoudDisplaySwaggerUiPage() {
		
		String content = given()
							.basePath("/swagger-ui/index.html")
							.port(TestConfig.SERVER_PORT)
							.when()
								.get()
							.then()
								.statusCode(200)
							.extract()
								.body().asString();
		
		Assertions.assertTrue(content.contains("Swagger UI"));
		
	}

}

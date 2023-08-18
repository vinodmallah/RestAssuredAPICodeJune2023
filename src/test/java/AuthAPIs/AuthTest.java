package AuthAPIs;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class AuthTest {
	
	@BeforeTest
	public void allureReportSetup() {
		RestAssured.filters(new AllureRestAssured());
	}	
	
	private RequestSpecification getRequestSpecification() {
		
		return new RequestSpecBuilder()
				.setBaseUri("https://fakestoreapi.com")
				.setContentType(ContentType.JSON)
				.setBody("{\r\n"
						+ "    \"username\": \"mor_2314\",\r\n"
						+ "    \"password\": \"83r5^_\"\r\n"
						+ "}")
				.build();		
	}
	
	
	
	@Test
	public void jwtAuthTest() {
		
		String jwtResponseToken = 	given()
										.spec(getRequestSpecification())
									.when()
										.post("/auth/login")
									.then()
										.assertThat()
											.statusCode(200)
										.and()
											.extract()
												.body().path("token");
		
		System.out.println("JWT token : "+jwtResponseToken);	
		
	}
	
	
	@Test
	public void basicAuthTest() {
		baseURI = "https://restful-booker.herokuapp.com/auth";
		
		String basicAuthToken = given().log().all()
									.contentType(ContentType.JSON)
									.auth().basic("admin", "password123")
								.when().log().all()
									.post()
								.then().log().all()
									.assertThat()
										.statusCode(200)
									.extract()
										.body().path("token");
		
		System.out.println("Basic Auth Token : "+basicAuthToken);		
	}
	
	@Test
	public void preemptiveAuthTest() {
		
		baseURI = "https://the-internet.herokuapp.com";
		
		String basicAuthToken = given().log().all()
									.contentType(ContentType.JSON)
									.auth().preemptive().basic("admin", "admin")
								.when().log().all()
									.get("/basic_auth")
								.then().log().all()
									.assertThat()
										.statusCode(200)
									.extract()
										.body().asString();
		
		System.out.println("Preemptive Auth Token : "+basicAuthToken);		
	}
	
	
	@Test
	public void digestAuthTest() {
		
		baseURI = "https://the-internet.herokuapp.com";
		
		String basicAuthToken = given().log().all()
									.contentType(ContentType.JSON)
									.auth().digest("admin", "admin")
								.when().log().all()
									.get("/basic_auth")
								.then().log().all()
									.assertThat()
										.statusCode(200)
									.extract()
										.body().asString();
		
		System.out.println("Preemptive Auth Token : "+basicAuthToken);		
	}
	

}

package GETAPIs;

import org.testng.annotations.Test;

import SpecificationConcept.RequestSpecBuilderTest;
import SpecificationConcept.ResponseSpecBuilderTest;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class GETAPIsWithRequestResponseSpecBuilderTest {
	
	@Test
	public void getUsers_with_RequestSpec() {		
		
		given().log().all()
			.spec(RequestSpecBuilderTest.getRequestSpecification())
				.get("public/v2/users")
					.then().log().all()
						.assertThat()
							.statusCode(200);
	}	
	
	@Test
	public void getUsers_with_QueryParam_RequestSpec() {		
		
		given().log().all()
			.spec(RequestSpecBuilderTest.getRequestSpecification())
				.queryParam("name", "Naveen")
				.queryParam("status", "active")
					.get("/public/v2/users")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.contentType(ContentType.JSON)
											.and()
												.body("$.size()", equalTo(3));
						
	}
	
		
	
	@Test
	public void getUserAPI_Request_Reponse_Spec_200_BuilderTest() {
			
		given().log().all()
				.spec(RequestSpecBuilderTest.getRequestSpecification())
					.when().log().all()
						.get("/public/v2/users")
							.then().log().all()
								.assertThat()
									.spec(ResponseSpecBuilderTest.resposeSpec_200_OK());		
	}

}

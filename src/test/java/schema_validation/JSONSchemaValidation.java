package schema_validation;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.GoREST_AddNewUser_POJO_Lombok;

public class JSONSchemaValidation {

	public static RequestSpecification setupRequestSpecification() {
		return new RequestSpecBuilder().setBaseUri("https://gorest.co.in").setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer a75c288084b24c784b140a94a610ee4194d585d0419c1eb84d6c63eeb7cb1dc2")
				.build();
	}

	public static String getRandomEmail() {
		return "apiRamomEmail" + System.currentTimeMillis() + "@gmail.com";
	}

	@Test
		public void goRestAPI_CreateNewUser_ScehemaValidation() {
			
			
			GoREST_AddNewUser_POJO_Lombok newUser = new GoREST_AddNewUser_POJO_Lombok("VinodMallah",getRandomEmail(),"male","active");
			
			given()
				.spec(setupRequestSpecification())
				.body(newUser)
			.when()
				.post("/public/v2/users/")
			.then()
				.assertThat()
					.statusCode(201)
					.and()
					.body(matchesJsonSchemaInClasspath("createNewUserJSONSchema.json"));		
	}

}

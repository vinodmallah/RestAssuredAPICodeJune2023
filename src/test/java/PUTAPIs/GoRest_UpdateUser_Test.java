package PUTAPIs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.GoREST_AddNewUser_POJO_Lombok;

public class GoRest_UpdateUser_Test {
	
	
	public static RequestSpecification setupRequestSpecification() {		
		return new RequestSpecBuilder()
				.setBaseUri("https://gorest.co.in")
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer a75c288084b24c784b140a94a610ee4194d585d0419c1eb84d6c63eeb7cb1dc2")
				.build();		
	}
	
	
	public static String getRandomEmail() {
		return "apiRamomEmail"+System.currentTimeMillis()+"@gmail.com";
	}
	
	
	
	@Test
	public void updateUser_PUT_API_Test() {
		
		// 1- Create a New User - POST Call
		
		GoREST_AddNewUser_POJO_Lombok newUser = new GoREST_AddNewUser_POJO_Lombok("VinodMallah",getRandomEmail(),"male","active");
		
		int newUserID = given()
							.spec(setupRequestSpecification())
							.body(newUser)
						.when()
							.post("/public/v2/users/")
						.then()
							.assertThat()
								.statusCode(201)
								.and()
								.extract()
								.path("id");
		
		System.out.println("New User ID :"+newUserID);
		
		// 2- Validate New User is Created - GET call
		
		given()
			.spec(setupRequestSpecification())
		.when()
			.get("/public/v2/users/"+newUserID)
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.and()
				.body("id", equalTo(newUserID));
		
		
		//3 - Update user details - PUT call
	
		newUser.setGender("female");
		newUser.setStatus("inactive");
		
		given()
			.spec(setupRequestSpecification())
			.body(newUser)
		.when()
			.put("/public/v2/users/"+newUserID)
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.and()
				.body("id", equalTo(newUserID))
				.and()
				.body("name", equalTo(newUser.getName()))
				.and()
				.body("gender", equalTo(newUser.getGender()))
				.and()
				.body("status", equalTo(newUser.getStatus()));		
	}	

}

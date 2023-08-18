package POSTAPIs;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.*;
import pojo.GoREST_AddNewUser_POJO;

import static org.hamcrest.Matchers.*;

public class GoRest_AddNewUser_Test {	
	
	private static RequestSpecification getRequestSpecification() {
		return new RequestSpecBuilder()
				.setBaseUri("https://gorest.co.in")
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer a75c288084b24c784b140a94a610ee4194d585d0419c1eb84d6c63eeb7cb1dc2")
				.build();
	}
	
	@Test
	public void addNewUserTest_Using_JsonFile() {
		
		//Step 1 : Add a new User -- POST call
		
		int newUserID = given().log().all()
							.spec(getRequestSpecification())
							.body(new File(".\\src\\test\\resources\\data\\newUserGoRest.json")).
						when().log().all()
							.post("/public/v2/users/").
						then().log().all()
							.assertThat()
							.statusCode(201)
							.extract()
								.path("id");
		
		System.out.println("New User ID : "+newUserID);				
		
		
		//Step 2 : Verify the added user -- GET call
		
		given().log().all()
			.spec(getRequestSpecification()).
		when().log().all()
			.get("/public/v2/users/"+newUserID).
		then().log().all()
			.assertThat()
			.statusCode(200)
			.and()
			.body("id", equalTo(newUserID));			
		
	}
	
	private String getRandomEmailID() {
		return "apiAutomation"+System.currentTimeMillis()+"@gmail.com";
	}
	
	@Test
	public void addNewUserTest_Using_POJOClass() {
		
		GoREST_AddNewUser_POJO newUserObj = new GoREST_AddNewUser_POJO("Vinod",getRandomEmailID(),"male","active");
		
		//Step 1 : Add a new User -- POST call
		
		int newUserID = given().log().all()
							.spec(getRequestSpecification())
							.body(newUserObj).
						when().log().all()
							.post("/public/v2/users/").
						then().log().all()
							.assertThat()
							.statusCode(201)
							.extract()
								.path("id");
		
		System.out.println("New User ID : "+newUserID);				
		
		
		//Step 2 : Verify the added user -- GET call
		
		given().log().all()
			.spec(getRequestSpecification()).
		when().log().all()
			.get("/public/v2/users/"+newUserID).
		then().log().all()
			.assertThat()
			.statusCode(200)
			.and()
			.body("id", equalTo(newUserID))	
			.and()
			.body("email", equalTo(newUserObj.getEmail()));
		
	}
	
	
	

}

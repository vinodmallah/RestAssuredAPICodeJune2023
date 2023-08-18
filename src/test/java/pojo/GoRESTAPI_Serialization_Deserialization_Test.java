package pojo;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GoRESTAPI_Serialization_Deserialization_Test {

	private RequestSpecification getRequestSpecification() {
		return new RequestSpecBuilder().setBaseUri("https://gorest.co.in").setContentType(ContentType.JSON)
				.addHeader("Authorization", "Bearer a75c288084b24c784b140a94a610ee4194d585d0419c1eb84d6c63eeb7cb1dc2")
				.build();
	}

	private String getRandomEmailID() {
		return "apiAutomation" + System.currentTimeMillis() + "@gmail.com";
	}

	@Test
	public void createNewUserTest_with_POJO_Lombok() {

		GoREST_AddNewUser_POJO_Lombok newUserData = new GoREST_AddNewUser_POJO_Lombok("Vinod", getRandomEmailID(),
				"male", "active");

		// 1 - Create User -- POST call
		Response postResponse = given().spec(getRequestSpecification()).body(newUserData) // Serialization
				.when().post("/public/v2/users/");

		int userID = postResponse.jsonPath().get("id");
		System.out.println("NewUserID : " + userID);

		// 2 - Get Created User -- GET call
		Response getResponse = given().spec(getRequestSpecification()).when().get("/public/v2/users/" + userID);

		getResponse.prettyPrint();

		// De-serialization
		GoREST_AddNewUser_POJO_Lombok reponseUserData = new Gson().fromJson(getResponse.getBody().asString(),
				GoREST_AddNewUser_POJO_Lombok.class);
		System.out.println(reponseUserData);
		Assert.assertEquals(Integer.valueOf(userID), reponseUserData.getId(), "User ID did not match");
		Assert.assertEquals(newUserData.getName(), reponseUserData.getName(), "Username did not match");
		Assert.assertEquals(newUserData.getEmail(), reponseUserData.getEmail(), "Email did not match");
		Assert.assertEquals(newUserData.getGender(), reponseUserData.getGender(), "Gender did not match");
		Assert.assertEquals(newUserData.getStatus(), reponseUserData.getStatus(), "Status did not match");

	}

}

package POSTAPIs;

import org.testng.Assert;
import java.io.*;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.BookingAPI_Creds_POJO;

import static io.restassured.RestAssured.*;

public class BookingAuthTest {
	
	public static RequestSpecification getBookingPostCallRequestSpecification() {
		return new RequestSpecBuilder()
				.setBaseUri("https://restful-booker.herokuapp.com")
				.setContentType(ContentType.JSON)
				.build();
	}	

	@Test
	public void getBookingAuthToken() {
		
		String tokenID = given().log().all()							
							.spec(getBookingPostCallRequestSpecification())
								.body("{\r\n"
										+ "    \"username\" : \"admin\",\r\n"
										+ "    \"password\" : \"password123\"\r\n"
										+ "}")
									.when().log().all()
										.post("/auth")
											.then().log().all()
												.assertThat()
													.statusCode(200)
														.extract()
															.path("token");
		
		System.out.println("Token ID : "+tokenID);		
		Assert.assertNotNull(tokenID, "TokenID is null.");			
	}	
	
	
	@Test
	public void getBookingAuthToken_Using_JsonFile() {
		
		String tokenID = given().log().all()							
							.spec(getBookingPostCallRequestSpecification())
								.body(new File(".\\src\\test\\resources\\data\\basicAuth.json"))
									.when().log().all() 
										.post("/auth")
											.then().log().all()
												.assertThat()
													.statusCode(200)
														.extract()
															.path("token");
		
		System.out.println("Token ID : "+tokenID);		
		Assert.assertNotNull(tokenID, "TokenID is null.");			
	}
	
	
	@Test
	public void getBookingAuthToken_Using_POJOClass() {
		
		BookingAPI_Creds_POJO credObj = new BookingAPI_Creds_POJO("admin","password123");
		
		String tokenID = given().log().all()							
							.spec(getBookingPostCallRequestSpecification())
								.body(credObj)
									.when().log().all() 
										.post("/auth")
											.then().log().all()
												.assertThat()
													.statusCode(200)
														.extract()
															.path("token");
		
		System.out.println("Token ID : "+tokenID);		
		Assert.assertNotNull(tokenID, "TokenID is null.");			
	}
	
}

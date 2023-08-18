package POSTAPIs;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusAPI_OAuth2_Workflow {
	
	private static String accessToken;	
	
	@BeforeTest
	private void getAccessToken() {		
		
		baseURI = "https://test.api.amadeus.com";
		
		accessToken=given()
						.header("Content-Type", "application/x-www-form-urlencoded")
						.formParam("grant_type", "client_credentials")
						.formParam("client_id", "b1YosBaDDok6TKiLBCb4tX1l2BUKTKv1")
						.formParam("client_secret", "Jk0WZ7WNeefOMzsz")
					.when()
						.post("/v1/security/oauth2/token")
					.then().log().all()
						.extract().path("access_token");	
	}
	
	@Test
	public void getFlightDetailsInfoTest() {
		
		Response flightDataResponse = given().log().all()
										.header("Authorization","Bearer "+accessToken)
										.queryParam("origin", "PAR")
										.queryParam("maxPrice", 200)
									.when()
										.get("/v1/shopping/flight-destinations")
									.then().log().all()
										.assertThat() 
										.statusCode(200)
										.and()
										.extract().response(); 
		
		JsonPath js = flightDataResponse.jsonPath();
		
		List<String> flightOrigin = js.getList("data.origin",String.class);	
		List<String> flightDestination = js.getList("data.destination",String.class);
		List<Float> flightPrice = js.getList("data.price.total",Float.class);
		List<String> responseDataSize = js.getList("data.type",String.class);
		
		System.out.println("Flight Origin   |  Flight Destination  | Flight Price  |");
		for(int i = 0; i<responseDataSize.size();i++) {
			System.out.println(flightOrigin.get(i)+"             |  "+flightDestination.get(i)+"                 |  "+ flightPrice.get(i)+"       |");
		}		
		
	} 

}

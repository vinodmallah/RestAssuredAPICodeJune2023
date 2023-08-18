package GETAPIs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class GetAPIWithPathParam {
	
	@Test
	public void getDataWithPathParam_YearTest() {
		
		baseURI = "https://ergast.com";
		
		given().log().all()
			.pathParam("year","2022")
					.when().log().all()
						.get("/api/f1/{year}/circuits.json")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.contentType(ContentType.JSON)
												.and()
													.body("MRData.CircuitTable.season", equalTo("2022"))
														.body("MRData.CircuitTable.Circuits",hasSize(22));												
		
	}
	
	
	@DataProvider
	public Object[][] getCircuitYearData() {
		return new Object[][] {
			{"2016",21},
			{"2017",20},
			{"1966",9},
			{"2022",22}
		};
	}
	
	
	@Test(dataProvider = "getCircuitYearData")
	public void getDataWithPathParam_Year_DataProvider(String seasonYear,int totalCircuits) {
		
		baseURI = "https://ergast.com";
		
		given().log().all()
			.pathParam("year",seasonYear)
					.when().log().all()
						.get("/api/f1/{year}/circuits.json")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.contentType(ContentType.JSON)
												.and()
													.body("MRData.CircuitTable.season", equalTo(seasonYear))
														.body("MRData.CircuitTable.Circuits",hasSize(totalCircuits));
												
		
	}

}

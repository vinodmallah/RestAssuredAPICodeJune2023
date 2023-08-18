package GETAPIs;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class GETAPIRequestTestWithBDD {

	@Test
	public void getUsersTest() {
		given().log().all().when().log().all().get("https://gorest.co.in/public/v2/users/").then().log().all()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.header("Connection", "keep-alive").and().body("$.size()", equalTo(10)).and()
				.body("id", is(notNullValue())).and().body("status", hasItem("active"));

	}

	@Test
	public void getSpecificUserUsingQueryParametersTest() {
		baseURI = "https://gorest.co.in";
		given().log().all()
				.header("Authorization", "Bearer a75c288084b24c784b140a94a610ee4194d585d0419c1eb84d6c63eeb7cb1dc2")
				.queryParam("name", "Naveen").queryParam("status", "active").when().log().all().get("/public/v2/users")
				.then().log().all().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.header("Connection", "keep-alive").and().body("$.size()", equalTo(3)).and()
				.body("id", is(notNullValue())).and().body("status", hasItem("active"));

	}

	@Test
	public void getProductAPI_With_ExtractBody() {

		baseURI = "https://fakestoreapi.com";
		Response response = given().queryParam("limit", "10").when().get("/products");
		JsonPath js = response.jsonPath();

		int id = js.getInt("[1].id");
		System.out.println("First ID : " + id);

		String bookTitle = js.getString("[1].title");
		System.out.println("Book Title : " + bookTitle);

		float bookPrice = js.getFloat("[1].price");
		System.out.println("Book Price : " + bookPrice);

		float bookRating = js.getFloat("[0].rating.rate");
		System.out.println("Book Rating : " + bookRating);

	}

	@Test
	public void getProductAPI_With_ExtractBodyDataArray() {

		baseURI = "https://fakestoreapi.com";
		Response response = given().queryParam("limit", "10").when().get("/products");
		
		JsonPath js = response.jsonPath(); // Json Array

		List<Integer> idList = js.getList("id");
		System.out.println("All ID : " + idList);

		List<String> titleList = js.getList("title");
		System.out.println("Book Title List : " + titleList);

		List<Float> priceList = js.getList("price", Float.class);
		System.out.println("Book Price List: " + priceList);

		List<Float> bookRatingList = js.getList("rating.rate", Float.class);
		System.out.println("Book Rating List: " + bookRatingList);

		for (int i = 0; i < idList.size(); i++) {
			System.out.println("ID : " + idList.get(i) + " Title : " + titleList.get(i) + " Price : " + priceList.get(i)
					+ " Rating Rate" + bookRatingList.get(i));
		}

	}

	@Test
	public void getUsersAPI_With_ExtractBody_WithJson() {

		baseURI = "https://gorest.co.in";
		Response response = given().log().all()
								.when().log().all()
									.get("/public/v2/users/3723969");
		
		JsonPath js = response.jsonPath(); // Json Object

		System.out.println("UserID  : " + js.getLong("id"));
		System.out.println("Name    : " + js.getString("name"));
		System.out.println("Email   : " + js.getString("email"));
		System.out.println("Gender  : " + js.getString("gender"));
	}
	
	
	@Test
	public void getUsersAPI_With_ExtractBody_WithJsonExtract() {

		baseURI = "https://gorest.co.in";
		String name = given().log().all()
								.when().log().all()
									.get("/public/v2/users/3723969")
										.then().log().all()
											.extract().path("name");
		System.out.println("Extract Name from JSON : "+name);
		
		
		Response response = given().log().all()
								.when().log().all()
									.get("/public/v2/users/3723969")
										.then()
											.extract().response();
		
		System.out.println("Extract Email from JSON  : "+response.path("email"));
		System.out.println("Extract Status from JSON : "+response.path("status"));
		
	}
}

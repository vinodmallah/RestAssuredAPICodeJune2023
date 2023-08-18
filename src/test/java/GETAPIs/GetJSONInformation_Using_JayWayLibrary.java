package GETAPIs;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class GetJSONInformation_Using_JayWayLibrary {

	@Test
	public void getCircuitInformation() {

		baseURI = "https://ergast.com";

		Response response = given().log().all().when().get("/api/f1/2023/circuits.json");

		String responseString = response.asString();

		List<String> countrylist = JsonPath.read(responseString, "$..Circuits..country");
		System.out.println("Country List Size : " + countrylist.size());
		System.out.println("Country List : " + countrylist);

		int totalCircuits = JsonPath.read(responseString, "$.MRData.CircuitTable.Circuits.length()");
		System.out.println("Data Length : " + totalCircuits);
	}

	@Test
	public void getFakeProductInformation() {

		baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		response.prettyPrint();

		String responseString = response.asString();

		// Total Products Length
		int totalProducts = JsonPath.read(responseString, "$.length()");
		System.out.println("Total Products : " + totalProducts);

		// List of all Titles with Category
		List<String> titleList = JsonPath.read(responseString, "$..title");
		System.out.println("Products Title List : " + titleList);

		// List of all title with price having rating > 4 with 2 Attributes
		List<Map<String, Object>> goodRatingProductList = JsonPath.read(responseString,
				"$[?(@.rating.rate > 4)].[\"title\",\"price\"]");
		
		System.out.println("Good Rating Product List with Price : " + goodRatingProductList);

		for (Map<String, Object> product : goodRatingProductList) {

			String productTitle = String.valueOf(product.get("title"));
			Object price = (Object) product.get("price");

			System.out.println("Product Title : " + productTitle);
			System.out.println("Product Price : " + price);

		}

		System.out.println("------------------------------------------------------------------");

		// List of all title with price having rating > 4 with 3 Attributes
		List<Map<String, Object>> goodProductListWithRating = JsonPath.read(responseString,
				"$[?(@.rating.rate > 4)].[\"title\",\"price\",\"rate\",\"id\"]");

		System.out.println("Good Rating Product List with Price & Rating : " + goodProductListWithRating);

		for (Map<String, Object> productWithRating : goodProductListWithRating) {

			String productTitle = (String) productWithRating.get("title");
			Object price = (Object) productWithRating.get("price");
			Object id = (Object) productWithRating.get("id");
			Object rating = (Object) productWithRating.get("rate");

			System.out.println("Product Title : " + productTitle);
			System.out.println("Product Price : " + price);
			System.out.println("Product id : " + id); 
			System.out.println("Product Rating : " + rating);

		}
	}

}

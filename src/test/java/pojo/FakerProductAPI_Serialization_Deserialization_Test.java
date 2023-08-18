package pojo;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.response.Response;

public class FakerProductAPI_Serialization_Deserialization_Test {

	@Test
	public void getAllProducts_Deserialization_JSON_to_POJO() {

		baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		FakerProducts_POJO[] productsArray = new Gson().fromJson(response.getBody().asString(), FakerProducts_POJO[].class);

		for (FakerProducts_POJO product : productsArray) {

			System.out.println("---------------------------------------------------");

			System.out.println("Product ID : " + product.getId());
			System.out.println("Product Title : " + product.getTitle());
			System.out.println("Product Price : " + product.getPrice());
			System.out.println("Product Description : " + product.getDescription());
			System.out.println("Product Category : " + product.getCategory());
			System.out.println("Product Image : " + product.getImage());
			System.out.println("Product Rate : " + product.getRating().getRate());
			System.out.println("Product Count : " + product.getRating().getCount());
		}

	}
	
	
	
	@Test
	public void getAllProducts_Deserialization_JSON_to_POJO_With_Lombok() {

		baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		FakerProducts_POJO_Lombok[] productsArray = new Gson().fromJson(response.getBody().asString(), FakerProducts_POJO_Lombok[].class);

		for (FakerProducts_POJO_Lombok product : productsArray) {
			System.out.println("---------------------------------------------------");
			System.out.println("Product ID : " + product.getId());
			System.out.println("Product Title : " + product.getTitle());
			System.out.println("Product Price : " + product.getPrice());
			System.out.println("Product Description : " + product.getDescription());
			System.out.println("Product Category : " + product.getCategory());
			System.out.println("Product Image : " + product.getImage());
			System.out.println("Product Rate : " + product.getRating().getRate());
			System.out.println("Product Count : " + product.getRating().getCount());  
		}

	}

}

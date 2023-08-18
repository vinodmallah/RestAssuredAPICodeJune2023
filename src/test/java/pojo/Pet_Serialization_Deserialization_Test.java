package pojo;

import static io.restassured.RestAssured.given;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Pet_POJO_Lombok.Category;
import pojo.Pet_POJO_Lombok.Tags;

public class Pet_Serialization_Deserialization_Test {

	private RequestSpecification getRequestSpecification() {
		return new RequestSpecBuilder().setBaseUri("https://petstore.swagger.io").setContentType(ContentType.JSON)
				.build();
	}

	@Test
	public void createPetTest_with_POJO_Lombok() {

		List<String> photoUrlList = Arrays.asList("www.scoobydobydoo.com/images/sccob.jpg",
				"www.scoobydobydoo.com/images/shag.jpg");
		Tags tag1 = new Tags(4, "brown");
		Tags tag2 = new Tags(5, "black");
		List<Tags> tagsList = Arrays.asList(tag1, tag2);

		Pet_POJO_Lombok petObject = new Pet_POJO_Lombok(222, new Category(123, "Detective"), "Scooby", photoUrlList,
				tagsList, "available");

		Response postResponse = given().spec(getRequestSpecification()).body(petObject) // Serialization
				.when().post("/v2/pet");
		
		postResponse.prettyPrint();

		// De-serialization
		Pet_POJO_Lombok reponsePetData = new Gson().fromJson(postResponse.getBody().asString(),
				Pet_POJO_Lombok.class);
		System.out.println("PetID : "+reponsePetData.getId());
		System.out.println("PetName : "+reponsePetData.getName());
		System.out.println("PetStatus : "+reponsePetData.getStatus());
		System.out.println("Category ID : "+reponsePetData.getCategory().getId());
		System.out.println("Category Name : "+reponsePetData.getCategory().getName());
		System.out.println("PhotoUrls : "+reponsePetData.getPhotoUrls());
		System.out.println("Tag ID : "+reponsePetData.getTags().get(0).getId());
		System.out.println("Tag Name : "+reponsePetData.getTags().get(0).getName());
	}

}

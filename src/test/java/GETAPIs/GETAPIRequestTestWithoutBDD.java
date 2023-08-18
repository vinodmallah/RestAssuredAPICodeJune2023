package GETAPIs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GETAPIRequestTestWithoutBDD {

	RequestSpecification request;

	@BeforeTest
	public void setup() {
		RestAssured.baseURI = "https://gorest.co.in";
		request = RestAssured.given();
		request.header("Authorization", "Bearer a75c288084b24c784b140a94a610ee4194d585d0419c1eb84d6c63eeb7cb1dc2");
	}

	@Test
	public void getUserAPITest() {

		Response response = request.get("/public/v2/users/");

		int actualStatusCode = response.statusCode();
		System.out.println("Status Code : " + actualStatusCode);
		// Verification Point
		Assert.assertEquals(actualStatusCode, 200);

		String statusMessage = response.statusLine();
		System.out.println("Status Message : " + statusMessage);

		// fetch the body
		response.prettyPrint();

		// Fetch Respose Headers
		System.out.println(response.header("Content-Type"));

		List<Header> headers = response.headers().asList();
		headers.stream().forEach(System.out::println);

	}

	@Test
	public void getAllUserWithQueryParameterAPITest() {

		request.queryParam("name", "Naveen");
		Response response = request.get("/public/v2/users/");

		int actualStatusCode = response.statusCode();
		System.out.println("Status Code : " + actualStatusCode);
		// Verification Point
		Assert.assertEquals(actualStatusCode, 200);

		String statusMessage = response.statusLine();
		System.out.println("Status Message : " + statusMessage);

		// fetch the body
		response.prettyPrint();

	}

	@Test
	public void getAllUserWithQueryParameterMapAPITest() {

		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("name", "Naveen");
		queryMap.put("status", "active");
		request.queryParams(queryMap);

		Response response = request.get("/public/v2/users/");

		int actualStatusCode = response.statusCode();
		System.out.println("Status Code : " + actualStatusCode);
		// Verification Point
		Assert.assertEquals(actualStatusCode, 200);

		String statusMessage = response.statusLine();
		System.out.println("Status Message : " + statusMessage);

		// fetch the body
		response.prettyPrint();

	}

}

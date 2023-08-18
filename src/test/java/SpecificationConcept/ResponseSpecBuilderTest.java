package SpecificationConcept;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;

public class ResponseSpecBuilderTest {
	
	public static ResponseSpecification resposeSpec_200_OK() {		
		return new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.expectHeader("referrer-policy", "strict-origin-when-cross-origin")
				.expectBody("$.size()",equalTo(10))
				.expectBody("id",hasSize(10))
				.build();
	}	
	
	public static ResponseSpecification resposeSpec_201_Created() {		
		return new ResponseSpecBuilder()
				.expectStatusCode(201)
				.expectContentType(ContentType.JSON)
				.expectHeader("referrer-policy", "strict-origin-when-cross-origin")
				.build();
	}	
	
	
	public static ResponseSpecification resposeSpec_401_AuthenticationFailure() {		
		return new ResponseSpecBuilder()
				.expectStatusCode(401)
				.expectHeader("referrer-policy", "strict-origin-when-cross-origin")
				.build();
	}
}

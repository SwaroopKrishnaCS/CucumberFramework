package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.*;

import static io.restassured.RestAssured.*;


public class ViewStoreStepDefinition {

	Response response;
	
	@Given("store API is available")
	public void store_api_is_available() {
		System.out.println("Given Method");
	    baseURI = "http://localhost:3030/";
	}

	@When("I invoke stores api with get method")
	public void i_invoke_stores_api_with_get_method() {
		response =  given().log().all().when().get("stores");
		
	    System.out.println("When Method");
	}

	@Then("the response code should be {int}")
	public void the_response_code_should_be(Integer int1) {
	   System.out.println("Then Method");
	   Assert.assertEquals(Long.toString(response.statusCode()), Long.toString(int1));
	}
	
	@When("I invoke {string} api with get method")
	public void invokeSingleStoreApi(String endpoint) {
		
		response = given().log().all().when().get(endpoint,4);
		
	}
	
	@When("I invoke stores api with get method and {int}")
	public void i_invoke_stores_api_with_get_method(int limit) {
		response = (Response) given().log().all().queryParam("$limit",limit).when().get("stores");
		
	}

}

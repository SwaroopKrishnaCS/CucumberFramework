package stepDefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import utils.JsonReader;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;


public class CreateStoreStepDefinition {

	Response response;
	JSONObject requestObject;
	
	@When("I invoke stores api with post method")
	public void invokeStoresApiWithPostMethod() {
		
		response = given().log().all().contentType("application/json").body(requestObject.toString()).when().post("stores");
		
	}
	
	@Given("create request for the method using following values")
	public void createRequest(DataTable datatable) {
		
		Map<String, String> reqParamsMap = datatable.asMaps().get(0);
		
		requestObject = new JSONObject();
		requestObject.put("name",reqParamsMap.get("name"));
		requestObject.put("address",reqParamsMap.get("address"));
		requestObject.put("city",reqParamsMap.get("city"));
		requestObject.put("state",reqParamsMap.get("state"));
		requestObject.put("zip",reqParamsMap.get("zip"));
		
	}
	
	@Then("the response code for create api should be {int}")
	public void the_response_code_should_be(Integer int1) {
	   System.out.println("Then Method");
	   Assert.assertEquals( Long.toString(int1),Long.toString(response.statusCode()));
	}
	
	@Given("populate the request with json from {string}")
	public void createRequestFromjson(String fileName) {
		
		requestObject = JsonReader.readJsonFile(fileName)	;
		
	}
}

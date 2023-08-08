package stepDefinition;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.createIssueRequest.CreateIssueRequest;
import model.createIssueResponses.CreateIssueMetadataResponse;
import model.createIssueResponses.Issuetype;
import utils.JsonReader;

import static io.restassured.RestAssured.*;

import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CreateIssueAPIStepDefinition {

	Response response;
	String projectId;
	String issueId;
	Gson gson = new Gson();
	CreateIssueMetadataResponse createMetadataResponse ;
	
	@When("I invoke the CreateIssueMetadata API")
	public void i_invoke_the_create_issue_metadata_api() {
	    // Write code here that turns the phrase above into concrete actions
	  baseURI = "https://katep.atlassian.net";
	 response =  given().header("Authorization","Basic a2F0ZXBxYUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbGd2bjJTdTdmWG9ZMTNnLU9OMmpjZWVScGZ0bWNfeGhkZFN0QmptLWwwd1pyQ0NMVTlHSXFJQTFPWlB4UmxPUXBXVVN6RUlvMXM3U29Oem5lOWF3YkF5UEZ3endXS0c0QWlORG9xeU1MYU53SnZFY0xkMzdfZzhrbnR2YlNvYy0tYTNSdEdHVllLQzNPQjQ4eDcwU2t3Z2dkQnQta2JqeENiVG56cnBMMlc4PTcxRTcxMEMx").
			 		contentType("application/json").get("/rest/api/3/issue/createmeta");
	}

	@Then("I extract the projectId and issueTypeId")
	public void i_extract_the_project_id_and_issue_type_id() {
		
		
		//Step 2 - Parse the response and store the values in CreateIssueMetadataResponse pojo class
		createMetadataResponse = response.as(CreateIssueMetadataResponse.class);
		
		//Step 3 - Extract the values from the pojo class
		
		projectId = createMetadataResponse.getProjects().get(0).getId();
		System.out.println("projectId : "+projectId);
		
		List<Issuetype> issueTypes = createMetadataResponse.getProjects().get(0).getIssuetypes();
		
		for(Issuetype issuetype: issueTypes) {
			
			if(issuetype.getName().equals("Story")) {
				issueId = issuetype.getId();
			}
		}
		
		System.out.println("issueid : "+issueId);
		 
	}

	@When("i invoke the createIssueAPI")
	public void i_invoke_the_create_issue_api() {
	   
		//Read the json file
		JSONObject jsonObject = JsonReader.readJsonFile("CreateIssue.json");
		
		//Convert this Json to a pojo class
		
		CreateIssueRequest createIssueRequest = gson.fromJson(jsonObject.toString(), CreateIssueRequest.class);
		
		// set in the dynamic values
		
		createIssueRequest.getFields().getProject().setId(projectId);
		createIssueRequest.getFields().getIssuetype().setId(issueId);
		
		response = given().header("Authorization","Basic a2F0ZXBxYUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbGd2bjJTdTdmWG9ZMTNnLU9OMmpjZWVScGZ0bWNfeGhkZFN0QmptLWwwd1pyQ0NMVTlHSXFJQTFPWlB4UmxPUXBXVVN6RUlvMXM3U29Oem5lOWF3YkF5UEZ3endXS0c0QWlORG9xeU1MYU53SnZFY0xkMzdfZzhrbnR2YlNvYy0tYTNSdEdHVllLQzNPQjQ4eDcwU2t3Z2dkQnQta2JqeENiVG56cnBMMlc4PTcxRTcxMEMx").
		 		contentType("application/json").body(createIssueRequest).log().all().post("/rest/api/3/issue/");
		
		
	    
	}

	@Then("verify the story id is present in response")
	public void verify_the_story_id_is_present_in_response() {
	    // Write code here that turns the phrase above into concrete actions
	    Assert.assertNotNull(response.body().jsonPath().getString("id"));
	}
	
	@Then("the response code should be {int}")
	public void the_response_code_should_be(Integer int1) {
	   System.out.println("Then Method");
	   Assert.assertEquals(Long.toString(response.statusCode()), Long.toString(int1));
	}
	
}

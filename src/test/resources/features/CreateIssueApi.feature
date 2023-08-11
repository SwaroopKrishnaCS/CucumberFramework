Feature:Create Issue APIs Tests

Background:
When I invoke the CreateIssueMetadata API
Then validate the response with schema "CreateIssueMetaDataSchema.json"
And the response code should be 200
And I extract the projectId and issueTypeId


@CreateStory
Scenario: Test Create Issue APIs
When i invoke the createIssueAPI 
Then the response code should be 201
And verify the story id is present in response 
 

Scenario Outline: Test Create Issue APIs
When I invoke the CreateIssueMetadata API
Then the response code should be 200
#And I extract the projectId and <issueType>
#When i invoke the createIssueAPI 
#Then the response code should be 201
#And verify the story id is present in response 
# Examples:
 #|issueType|
 #|Story|
 #|Bug|
 
 @InvalidSchemaTest
 Scenario: Test for invalidSchema
 When I invoke the CreateIssueMetadata API
Then validate the response with schema "InvalidSchema.json"
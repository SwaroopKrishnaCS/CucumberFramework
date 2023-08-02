@tag
Feature: View Stores
  As a user I should be able to view the different stores

  @tag1
  Scenario: Get All Stores
    Given store API is available
    When I invoke stores api with get method
    Then the response code should be 200


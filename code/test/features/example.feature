Feature: Example BDD Test for Application

  Scenario: Validate application response for a basic request
    Given the application is running
    When I send a GET request to "/chat"
    Then I should receive a status 200
    And the response should contain "Hi! I am your AI assistant."

  Scenario: Generate BDD test cases using the AI
    Given the application is running
    When I send a POST request to "/chat/generate" with the context "Perform fraud analysis"
    Then I should receive a status 200
    And the response should contain "Test cases generated successfully"

  Scenario: Execute BDD test cases
    Given BDD test cases are generated
    When I send a POST request to "/chat/execute"
    Then I should receive a status 200
    And the response should contain "Tests executed successfully"

  Scenario: Generate and Download Report
    Given BDD test cases are executed
    When I send a POST request to "/chat/report"
    Then I should receive a status 200
    And the response should contain "Report generated successfully"
    And I should be able to download the report from "/chat/download-report"

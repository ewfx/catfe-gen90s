Feature: KYC Verification for Loan Processing

  Scenario: Successfully submit KYC details and get approved
    Given a user with ID "user123" and valid KYC details
    When the user submits their KYC details
    Then the KYC status should be "APPROVED"
    And the risk score should be less than 50

  Scenario: Submit KYC details and get rejected due to high risk
    Given a user with ID "user456" and risky KYC details
    When the user submits their KYC details
    Then the KYC status should be "REJECTED"
    And the risk score should be greater than or equal to 50

  Scenario: Check KYC status for an existing user
    Given a user with ID "user123" has submitted KYC details
    When the KYC status is checked for "user123"
    Then the KYC status should be "APPROVED"
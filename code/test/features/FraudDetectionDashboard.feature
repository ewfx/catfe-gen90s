Feature: Payment Transactions Fraud Detection Dashboard

  Background:
    Given the fraud detection dashboard is accessible
    And the system is connected to the payment transaction data
    And fraud detection algorithms are enabled

  Scenario: View Overall Fraud Summary
    When the user navigates to the dashboard
    Then the dashboard should display the total number of transactions
    And it should display the total number of flagged transactions
    And it should show the fraud detection accuracy percentage

  Scenario: View Transaction Details
    Given the user is on the fraud detection dashboard
    When the user selects a specific transaction
    Then the transaction details should be displayed
    And it should show the sender and receiver information
    And it should display the transaction amount, currency, and timestamp
    And the fraud risk score should be shown

  Scenario: Filter Transactions by Fraud Risk
    Given the user is on the fraud detection dashboard
    When the user applies a filter for high-risk transactions
    Then only transactions with a fraud risk score above 80% should be displayed

  Scenario: Generate Fraud Detection Report
    Given the user is on the fraud detection dashboard
    When the user clicks on the "Generate Report" button
    Then a detailed fraud detection report should be generated
    And it should contain transaction details, risk scores, and fraud classification

  Scenario: Receive Fraud Alerts
    Given the user is monitoring transactions on the dashboard
    When a transaction is detected with a fraud risk score above 90%
    Then a real-time fraud alert should be displayed
    And the user should have an option to investigate or flag the transaction

  Scenario: Visualize Fraud Trends
    Given the user is on the fraud detection dashboard
    When the user navigates to the fraud trends section
    Then the dashboard should display graphs of fraud activity over time
    And it should show geographical locations with high fraud rates
    And it should provide insights into common fraud patterns

  Scenario: Update Fraud Detection Rules
    Given the user has appropriate permissions
    When the user updates fraud detection rules using the dashboard
    Then the system should apply the new rules in real-time
    And future transactions should be evaluated based on the updated rules

  Scenario: Export Transaction Data
    Given the user is on the fraud detection dashboard
    When the user clicks on the "Export Data" button
    Then the transaction data should be exported in a CSV format
    And the exported data should include transaction ID, risk score, and status

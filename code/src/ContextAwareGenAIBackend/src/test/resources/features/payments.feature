Feature: Payment Servicing using SWIFT ISO

  Background:
    Given a valid SWIFT ISO 20022 payment message
    And a system configured to process SWIFT ISO payments
    And a test environment with necessary infrastructure

  Scenario: Successful Payment Processing - Credit Transfer
    Given a sender account with sufficient funds
    And a receiver account with a valid BIC
    And the payment message contains:
      | Field          | Value                       |
      | Message Type   | pacs.008.001.08             |
      | Sender BIC      | BANKAXXX123                |
      | Receiver BIC    | BANKBXXX456                |
      | Amount          | 100.00                     |
      | Currency        | USD                         |
      | Payment Purpose | Goods Payment              |
      | Remittance Info | Invoice INV-2024-01        |
    When the system receives the SWIFT ISO payment message
    Then the payment processing should be successful
    And the sender account balance should be decreased by the amount and fees
    And the receiver account balance should be increased by the amount
    And a payment transaction record should be created
    And an audit log entry should be created with details of the payment
    And the system should generate an acknowledgement message (ACK)

  Scenario: Payment Processing with Insufficient Funds
    Given a sender account with insufficient funds
    And a receiver account with a valid BIC
    And the payment message contains:
      | Field          | Value                       |
      | Message Type   | pacs.008.001.08             |
      | Sender BIC      | BANKAXXX123                |
      | Receiver BIC    | BANKBXXX456                |
      | Amount          | 10000.00                   |
      | Currency        | USD                         |
      | Payment Purpose | Goods Payment              |
      | Remittance Info | Invoice INV-2024-01        |
    When the system receives the SWIFT ISO payment message
    Then the payment processing should be rejected
    And the sender account balance should remain unchanged
    And the receiver account balance should remain unchanged
    And an error message should be generated indicating insufficient funds
    And the system should generate a negative acknowledgement message (NACK)
    And an audit log entry should be created with the rejection reason

  Scenario: Payment Processing with Invalid Receiver BIC
    Given a sender account with sufficient funds
    And a receiver account with an invalid BIC
    And the payment message contains:
      | Field          | Value                       |
      | Message Type   | pacs.008.001.08             |
      | Sender BIC      | BANKAXXX123                |
      | Receiver BIC    | INVALIDBIC                 |
      | Amount          | 100.00                     |
      | Currency        | USD                         |
      | Payment Purpose | Goods Payment              |
      | Remittance Info | Invoice INV-2024-01        |
    When the system receives the SWIFT ISO payment message
    Then the payment processing should be rejected
    And the sender account balance should remain unchanged
    And the receiver account balance should remain unchanged
    And an error message should be generated indicating an invalid BIC
    And the system should generate a negative acknowledgement message (NACK)
    And an audit log entry should be created with the rejection reason

  Scenario: Payment Processing with Invalid Currency
    Given a sender account with sufficient funds
    And a receiver account with a valid BIC
    And the payment message contains:
      | Field          | Value                       |
      | Message Type   | pacs.008.001.08             |
      | Sender BIC      | BANKAXXX123                |
      | Receiver BIC    | BANKBXXX456                |
      | Amount          | 100.00                     |
      | Currency        | XYZ                         |
      | Payment Purpose | Goods Payment              |
      | Remittance Info | Invoice INV-2024-01        |
    When the system receives the SWIFT ISO payment message
    Then the payment processing should be rejected
    And the sender account balance should remain unchanged
    And the receiver account balance should remain unchanged
    And an error message should be generated indicating an invalid currency
    And the system should generate a negative acknowledgement message (NACK)
    And an audit log entry should be created with the rejection reason

  Scenario Outline: Different Message Types and Scenarios
    Given a sender account with sufficient funds
    And a receiver account with a valid BIC
    And the payment message contains:
      | Field          | Value                       |
      | Message Type   | <MessageType>               |
      | Sender BIC      | BANKAXXX123                |
      | Receiver BIC    | BANKBXXX456                |
      | Amount          | 100.00                     |
      | Currency        | USD                         |
      | Payment Purpose | Goods Payment              |
      | Remittance Info | Invoice INV-2024-01        |
    When the system receives the SWIFT ISO payment message
    Then the payment processing should be successful
    And the sender account balance should be decreased by the amount and fees
    And the receiver account balance should be increased by the amount
    And a payment transaction record should be created
    And an audit log entry should be created with details of the payment
    And the system should generate an acknowledgement message (ACK)

    Examples:
      | MessageType      |
      | pacs.008.001.08  |
      | pacs.009.001.08  |
      | camt.053.001.02  |

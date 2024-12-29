@AddHoliday
Feature: Add Holiday Functionality
  As a user, I want to add holidays with valid names and dates and receive appropriate messages for valid and invalid inputs.


  Scenario: Add a holiday with valid name and date
    Given I am on the "Add Holiday" page
    When I enter "Christmas" as the holidayName
    And I enter "2025-12-26" as the date
    And I click the "Save" button
    Then I should see the message "Successfully saved"

  Scenario: Add a holiday with special characters in the name
    Given I am on the "Add Holiday" page
    When I enter "@7Christmas" as the holidayName
    And I enter "2026-12-25" as the date
    And I click the "Save" button
    Then I should see the message "Successfully saved"

  Scenario: Attempt to save without entering any details
    Given I am on the "Add Holiday" page
    When I click the "Save" button
    Then I should see an error message "Name is required"

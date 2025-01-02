@SaveHoliday
Feature: Save Holidays
  This feature allows users to add a holiday with name and date and verify the outcomes.

  Scenario: Add a holiday with repeated data.
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year" and date "1925-07-13"
    And the user clicks on the "Save" button


  Scenario: Add a holiday without name
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "" and date "2026-06-09"
    And the user clicks on the "Save" button


  Scenario: Add a holiday without date
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "Independence Day" and date ""
    And the user clicks on the "Save" button


  Scenario: Add a holiday without name and date
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "" and date ""
    And the user clicks on the "Save" button


  Scenario: Add a holiday successfully.
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year" and date "1966-07-16"
    And the user clicks on the "Save" button



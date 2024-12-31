@SaveHoliday
Feature: Save Holidays
  This feature allows users to add a holiday with name and date and verify the outcomes.

  Scenario: Add a holiday successfully
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year" and date "2030-11-11"
    And the user clicks on the "Save" button
    Then the user should see a success message "Successfully Saved"
    And the user should be redirected back to the Holidays list page

  Scenario: Add a holiday without name
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "" and date "2030-07-04"
    And the user clicks on the "Save" button
    Then the user should see a success message "required"
    And the user should be redirected back to the Holidays list page


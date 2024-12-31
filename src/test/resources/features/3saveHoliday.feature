@SaveHoliday

Feature: Save Holidays
  This feature allows users to add a holiday with name and date and verify the outcomes.

  Scenario: Add a holiday successfully
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year" and date "2029-06-25"
    And the user clicks on the "Save" button
    Then the user should see a success message "Successfully Saved"
    And the user should be redirected back to the Holidays list page

  Scenario: Add a holiday with repeated data
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year" and date "2029-06-25"
    And the user clicks on the "Save" button
    Then the user should see a success message "Successfully Saved"
    And the user should be redirected back to the Holidays list page

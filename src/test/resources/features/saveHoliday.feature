@SaveHoliday
Feature: Save Holidays
  This feature allows users to add a holiday with name and date and verify the outcomes.

  Scenario: Add a holiday successfully
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year" and date "1999-05-07"
    And the user clicks on the "Save" button
    Then the user should see a success message "Successfully Saved"


  Scenario: Add a holiday without name
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "" and date "2000-24-07"
    And the user clicks on the "Save" button
    Then the user should see a success message "required"
    And the user should be redirected back to the Holidays list page

  Scenario: Add a holiday without date
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "Independence Day" and date ""
    And the user clicks on the "Save" button
    Then the user should see a success message "required"
    And the user should be redirected back to the Holidays list page

  Scenario: Add a holiday without name and date
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "" and date ""
    And the user clicks on the "Save" button
    Then the user should see an error message "required" for both name and date

  Scenario: Add a holiday with repeated data.
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year" and date "1999-05-07"
    And the user clicks on the "Save" button
    Then the user should see a success message "Successfully Saved"
    And the user should be redirected back to the Holidays list page

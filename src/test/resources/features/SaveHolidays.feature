Feature: Save Holidays in OrangeHRM

  @SaveHolidays
  Scenario Outline: User successfully adds a holiday
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "<holidayName>", date "<holidayDate>", selects "<holidayType>", and repeats annually
    And the user clicks on the "<buttonName>" button
    Then the user should see a success message "<successMessage>"

    Examples:
      | holidayName      | holidayDate | holidayType | buttonName | successMessage       |
      | Christmas        | 2024-12-25  | Full Day    | Save       | Successfully Saved   |
      | @123Christmas    | 2025-01-01  | Half Day    | Save       | UnSuccessfully Saved |

  @CancelHoliday
  Scenario: User cancels the holiday addition
    Given the user navigates to the Save Holidays page
    When the user enters holiday details with name "New Year", date "2025-01-01", selects "Full Day", and repeats annually
    And the user clicks on the "Cancel" button
    Then the user should be redirected back to the Holidays list page

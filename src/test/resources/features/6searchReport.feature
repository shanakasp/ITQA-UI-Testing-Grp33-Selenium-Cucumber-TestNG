@SearchReport
Feature: Report Search Functionality on OrangeHRM

  Scenario Outline: Search a report by name and verify the results
    Given User navigates to Reports module
    When User enters report name "<reportName>"
    And User selects a report from suggestions
    And User clicks the search button
    Then User verifies the "<expectedResult>" in the table
    And User navigates back to the dashboard

    Examples:
      | reportName                             | expectedResult |
      | All Employee Sub Unit Hierarchy Report | 1              |
      | Non Existent Report                    | 0              |

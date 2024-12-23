@Login
Feature: OrangeHRM Login Functionality

  Scenario Outline: Login to OrangeHRM
    Given User is on OrangeHRM login page
    When User enters username "<username>"
    And User enters password "<password>"
    And User clicks login button
    Then User sees "<expectedResult>"


    Examples:
      | username   | password     | expectedResult             |
      | invaliduser| admin123    | User name is wrong        |
      | Admin | wrongUser    | Password is wrong        |
      | Admin      | admin123     | is redirected to the dashboard |



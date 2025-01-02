Feature: OrangeHRM Login Functionality

  @SmokeTest
  Scenario: Successful Login to OrangeHRM
    Given User is on OrangeHRM login page
    When User enters username "Admin"
    And User enters password "admin123"
    And User clicks login button
    Then User should be redirected to dashboard
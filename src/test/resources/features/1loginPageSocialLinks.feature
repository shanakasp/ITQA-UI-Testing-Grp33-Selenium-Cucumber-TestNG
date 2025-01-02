@LoginPageURLCheck
Feature: OrangeHRM Social Media Links Verification
  Scenario: Verify social media links on OrangeHRM login page
  As a user of OrangeHRM
  I want to verify the social media links on the login page
  So that I can confirm they redirect to the correct pages

    Given User is on OrangeHRM login
    When User clicks on LinkedIn icon
    Then LinkedIn page should open with correct URL

    When User clicks on Facebook icon
    Then Facebook page should open with correct URL

    When User clicks on Twitter icon
    Then Twitter page should open with correct URL

    When User clicks on YouTube icon
    Then YouTube page should open with correct URL
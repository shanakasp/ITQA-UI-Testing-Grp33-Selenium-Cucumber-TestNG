@InputValidationMyInfo
Feature: Update User Info
  As a user, I want to update my info so that the changes are saved and validated

  Scenario Outline: Update user info and validate results
    Given User navigates to the "<pageName>" page
    When User enters "<firstName>" in the "First Name" field
    And User enters "<middleName>" in the "Middle Name" field
    And User enters "<lastName>" in the "Last Name" field
    And User clicks the save button myInfo
    Then User sees "<expectedResult>" myInfo

    Examples:
      | pageName           | firstName | middleName | lastName | expectedResult                       |
      | Personal Details   | 12345     | Smith      | Doe      | Invalid name format                  |
      | Personal Details   | John      | M          | Doe      | Success             |
      | Personal Details   | @#$%      | Smith      | Doe      | Invalid name format                  |
      | Personal Details   | John      |            | Doe      | Success           |

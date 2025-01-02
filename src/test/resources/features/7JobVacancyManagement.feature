@JobVacancy
Feature: Job Vacancy Management
  As an admin user
  I want to manage job vacancies
  So that I can maintain an updated list of job openings

  Background:
    Given User clicks on Recruitment in side menu bar for Recruitment
    Then User clicks on vacancies

  Scenario: Get all listed vacancies
    And Click on search button for see all the available jobs

  Scenario: Delete job vacancy
    Given User clicks on delete for job vacancies
    Then User confirms delete job vacancy

#
#  Scenario: Search for a Job Vacancy
#    When I enter "Software Engineer" in the Job Title field
#    And I click the "Search" button
#    Then I should see results matching "Software Engineer"
#
#  Scenario: Add a new Job Vacancy
#    When I click the "Add" button
#    And I fill in the required fields for a new job vacancy
#      | Job Title          | Hiring Manager      | Description            |
#      | Software Engineer  | John Doe            | Develops applications. |
#    And I click "Save"
#    Then I should see "Software Engineer" in the job vacancies list
#
#  Scenario: Edit an existing Job Vacancy
#    When I select the job vacancy "Software Engineer"
#    And I click "Edit"
#    And I update the Hiring Manager to "Jane Smith"
#    And I click "Save"
#    Then I should see the updated Hiring Manager as "Jane Smith"
#
#  Scenario: Delete a Job Vacancy
#    When I select the job vacancy "Software Engineer"
#    And I click "Delete"
#    Then "Software Engineer" should no longer appear in the vacancies list
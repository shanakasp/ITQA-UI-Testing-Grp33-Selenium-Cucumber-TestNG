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

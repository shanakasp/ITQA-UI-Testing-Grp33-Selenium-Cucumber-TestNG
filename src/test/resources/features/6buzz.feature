@BuzzComplete
Feature: OrangeHRM Buzz Functionality

  Background:
    Given User clicks on Buzz in side menu bar

  Scenario: Check post filtering functionalities Most Liked Posts
    When User clicks Most Liked Posts

  Scenario: Check post filtering functionalities Most Commented Posts
    When User clicks Most Commented Posts

  Scenario: Check post filtering functionalities Most Recent Posts
    When User clicks Most Recent Posts

  Scenario: User add new post
    When User add post "My name is Buddima. "
    Then User clicks on add post button

  Scenario: User edits the added post
    When User clicks three dots button
    And User click edit post from dropdown
    And Edit post like " Ok, I'm Buddima"
    Then Click on Post button

  Scenario: User adds a heart to the post
    When User add heart to a post

  Scenario: User adds a comment to the post
    When User add comment to a post
    Then User writes comment "Let's see. "

  Scenario: User deletes the added post
    When User clicks three dots button
    And User click delete post from dropdown
    Then User confirms delete comment
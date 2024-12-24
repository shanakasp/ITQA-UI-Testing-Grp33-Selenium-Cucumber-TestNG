@BuzzComplete
Feature: OrangeHRM Buzz Functionality

  Scenario: User clicks on Buzz in side menu bar and check functionalities related to a post
    Given User clicks on Buzz in side menu bar
    And User clicks Most Liked Posts
    And User clicks Most Commented Posts
    And User clicks Most Recent Posts

    Given User add post "We are going to test this works or not. "
    And User clicks on add post button

    Given User clicks three dots button
    When User click edit post from dropdown
    Then Edit post like " Ok, This works"
    And Click on Post button
    
    
    Given User add heart to a post
    Given User add comment to a post
    Then User writes comment "Let's see. "

    Given User clicks three dots button
    When User click delete post from dropdown
    Then  User confirms delete comment
    
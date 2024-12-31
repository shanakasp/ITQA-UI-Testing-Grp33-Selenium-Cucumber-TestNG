@BuzzComplete
Feature: OrangeHRM Buzz Functionality

  Scenario: User clicks on Buzz in side menu bar and check functionalities related to a post
    Given User clicks on Buzz in side menu bar
    Then User clicks Most Liked Posts
    Then User clicks Most Commented Posts
    Then User clicks Most Recent Posts

    Given User add post "My name is Buddima. "
    And User clicks on add post button

    Given User clicks three dots button
    When User click edit post from dropdown
    Then Edit post like " Ok, I'm Buddima"
    And Click on Post button

    Given User add heart to a post
    Given User add comment to a post
    Then User writes comment "Let's see. "

    Given User clicks three dots button
    When User click delete post from dropdown
    Then  User confirms delete comment




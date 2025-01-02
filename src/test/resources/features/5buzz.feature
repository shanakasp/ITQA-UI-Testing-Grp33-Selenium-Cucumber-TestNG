@BuzzComplete
Feature: OrangeHRM Buzz Functionality Check

  Scenario: Check post filtering functionalities

    Given User clicks on Buzz in side menu bar

    When User clicks Most Liked Posts
    Then User clicks Most Commented Posts
    And User clicks Most Recent Posts

    When User add post "My name is Buddima. "
    Then User clicks on add post button

    When User clicks three dots button
    And User click edit post from dropdown
    And Edit post like " Ok, I'm Buddima"
    Then Click on Post button

    When User add heart to a post

    When User add comment to a post
    Then User writes comment "Let's see. "

    When User clicks three dots button
    And User click delete post from dropdown
    Then User confirms delete comment
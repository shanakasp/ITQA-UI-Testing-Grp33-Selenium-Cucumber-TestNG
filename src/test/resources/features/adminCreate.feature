Feature: Add New User in Admin Module

  @EditAdminTest
  Scenario Outline: Edit existing user in admin module
    Given User clicks on Admin in side menu bar
    Then User clicks edit on the records table
    And User changes user name in admin edit to "<username>"
    And User clicks save button on Edit User Page
    Examples:
      | username |
      | buddima |

  @DeleteAdminTest
  Scenario: Delete user from admin module
    Given User clicks on Admin in side menu bar
    Then User checks current user count
    And User clicks delete on the records table
    Then User clicks confirm delete on popup
    And User checks relevant user delete or not

  @AddAdminTest
  Scenario Outline: Add a new user to the system in admin
    Given User clicks on Admin in side menu bar
    Then User clicks on the Add button in Admin Page
    When User selects user role as "<userRole>"
    And User sets the employee name to "<employeeName>"
    And User selects status as "<status>"
    And User sets the username to "<username>"
    And User sets the password to "<password>"
    And User confirms the password as "<password>"
    And User clicks the Save button
    Then User should see a success message

    Examples:
      | username | password       | employeeName | userRole | status  |
      | Buddima  | StrongPass123! | Lakshi J     | Admin    | Enabled |
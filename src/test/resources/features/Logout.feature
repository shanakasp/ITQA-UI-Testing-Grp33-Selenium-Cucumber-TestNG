Feature: OrangeHRM Login Functionality Check All the types

  @LogoutCheck
  Scenario: LogOut OrangeHRM check
    When User clicks Profile button check
    Then User clicks on logout button check
    And User logs out to login page check
    Then User clicks back button from browser check
    When User should not see "Dashboard" text again check
    And User terminates the process


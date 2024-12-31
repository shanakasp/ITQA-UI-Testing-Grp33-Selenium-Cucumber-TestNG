@ChangePassword
Feature: OrangeHRM Change Password Functionality


  Scenario Outline: Attempt to Change Password with Invalid Credentials
    When User navigates to Change Password
    And User enters current password "<currentPassword>"
    And User enters new password "<newPassword>"
    And User confirms new password "<confirmPassword>"
    Then User clicks Save button


    Examples:
      | currentPassword | newPassword | confirmPassword |
      | wrongpassword   | NewPass123! | NewPass123!     |
      | admin123        | short       | short           |
      | admin123        | NewPass123! | DifferentPass456! |

  Scenario: Successfully Change Password
    When User navigates to Change Password
    And User enters current password "admin123"
    And User enters new password "NewPass123!"
    And User confirms new password "NewPass123!"
    Then User clicks Save button
    Then User is logged out automatically
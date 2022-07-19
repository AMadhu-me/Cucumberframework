Feature: Login

  @smoke
  Scenario: Login with correct credentials
    Given Launch the application URL
    When Enter the Username and password
    When Click the Submit button
    Then close the browser

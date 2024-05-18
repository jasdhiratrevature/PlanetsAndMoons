Feature: Login

  Background:
    Given User is on Login page

  Scenario Outline: Login - Valid
    Given the user has an active an account with "validuser" and "validpassword"
    When  the user enters "<username>" as the username and "<password>" as the password
    And the user clicks on the Login button
    Then the user is directed to Home page

    Examples:
      | username  | password      |
      | validuser | validpassword |

  Scenario Outline: Login - Invalid - Wrong Password
    Given the user has an active an account with "validuser" and "validpassword"
    When  the user enters "<username>" as the username and "<password>" as the password
    And the user clicks on the Login button
    Then an error message should be displayed

    Examples:
      | username  | password      |
      | validuser | wrongpassword |

  Scenario Outline: Login - Invalid - Non-existing Username
    When  the user enters "<username>" as the username and "<password>" as the password
    And the user clicks on the Login button
    Then an error message should be displayed

    Examples:
      | username    | password      |
      | invaliduser | validpassword |

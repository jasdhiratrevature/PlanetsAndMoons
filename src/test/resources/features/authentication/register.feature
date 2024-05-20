Feature: Create New Account

  Background:
    Given the user is on the Register page

  # POSITIVE CASES
  Scenario Outline: Register - Valid Username & Password
    When the user enters <username> as the username and "validpassword" as the password
    And the user clicks on the Register button
    Then a successful alert should be displayed
    And the user is directed to Login page

    Examples:
      | username        |
      | "ALLUPPERCASE"  |
      | "alllowercase"  |
      | "trailingspace" |

  # NEGATIVE CASES
  Scenario Outline: Register - Invalid Username or Password
    When the user enters <username> as the username and <password> as the password
    And the user clicks on the Register button
    Then an error message should be displayed

    Examples:
      | username                                 | password                                 |
      | "nonascii123jósé"                        | "validpassword"                          |
      | "validuser"                              | "nonascii123josé"                        |
      | "usernametoolongmorethan31characters123" | "validpassword"                          |
      | "validuser"                              | "passwordtoolongmorethan31characters123" |
      | "Robert'; DROP TABLE users;--"           | "validpassword"                          |
      | "validuser"                              | "Robert'; DROP TABLE users;--"           |
      | ""                                       | "validpassword"                          |
      | "validuser"                              | ""                                       |
      | "alluppercase"                           | "invaliduser"                            |
      | "validuser"                              | ""                                       |

  Scenario: Register - Duplicate Username
    Given the username "alluppercase" and "invaliduser" already exists
    When the user enters "alluppercase" as the username and "invaliduser" as the password
    And the user clicks on the Register button
    Then an error message should be displayed

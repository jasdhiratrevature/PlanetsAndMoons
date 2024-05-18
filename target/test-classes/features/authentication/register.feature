Feature: Create New Account

  Background:
    Given the user is on the Register page

    #POSITIVE CASES
  Scenario Outline: Register - All Uppercase Username
    When the user enters "<username>" as the username and "validpassword" as the password
    And the user clicks on the Register button
    Then a successful alert should be displayed
    And the user is directed to Login page
    Examples:
      | username     |
      | ALLUPPERCASE |

  Scenario Outline: Register - All Lowercase Username
    When the user enters "<username>" as the username and "validpassword" as the password
    And the user clicks on the Register button
    Then a successful alert should be displayed
    And the user is directed to Login page

    Examples:
      | username     |
      | alllowercase |

  Scenario Outline: Register - Trailing Space Username
    When the user enters <username> as the username and "validpassword" as the password
    And the user clicks on the Register button
    Then a successful alert should be displayed
    And the user is directed to Login page

    Examples:
      | username               |
      | "   trailingspace    " |


    #NEGATIVE CASES
  Scenario Outline: Register - Non-ASCII Username
    When the user enters "<username>" as the username and "<password>" as the password
    And the user clicks on the Register button
    Then an error message should be displayed

    Examples:
      | username        | password        |
      | nonascii123jósé | invaliduser     |
      | invaliduser     | nonascii123josé |

  Scenario Outline: Register - Exceeding Maximum Length
    When the user enters "<username>" as the username and "<password>" as the password
    And the user clicks on the Register button
    Then an error message should be displayed

    Examples:
      | username                               | password                               |
      | usernametoolongmorethan31characters123 |                                        |
      | invaliduser                            | passwordtoolongmorethan31characters123 |


  Scenario Outline: Register - SQL Injection
    When the user enters "<username>" as the username and "<password>" as the password
    And the user clicks on the Register button
    Then an error message should be displayed

    Examples:
      | username                     | password                     |
      | Robert'; DROP TABLE users;-- | invaliduser                  |
      | invaliduser                  | Robert'; DROP TABLE users;-- |


  Scenario: Register - Username - Duplicate
    Given the username "alluppercase" and "invaliduser" already exists
    When the user enters "alluppercase" as the username and "invaliduser" as the password
    And the user clicks on the Register button
    Then an error message should be displayed

  Scenario Outline: Register - Empty Fields
    When the user enters <username> as the username and <password> as the password
    And the user clicks on the Register button
    Then an error message should be displayed

    Examples:
      | username      | password      |
      | ""            | "invaliduser" |
      | "invaliduser" | ""            |



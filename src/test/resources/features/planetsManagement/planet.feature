Feature: Add Planet

  Background:
    Given the user has an existing account
    And the user is logged in
    And the user is on the home page

  Scenario: Add Planet - Valid Cases
    Given the planet does not already exist
    And the Planet option is selected in the location select
    When the user enters "earth" in the planet input
    And clicks the submit planet button
    Then the planet should be added successfully to the Celestial Table
    And the planet name should be "earth"

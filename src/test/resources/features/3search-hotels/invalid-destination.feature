Feature: Destination search

  Scenario: Check the error message for invalid destination

    Given I am on the main page
    When I try to choose invalid destination
    Then Page should have message consistent with pattern: Sorry, we don't recogni(z|s)e that name.
Feature: Correct selection room
  Background:
    Given I am on hotel page

  Scenario: Check correct room selection
    When I choose for booking some room
    Then Show information about booking, which corresponds to the information about selected room
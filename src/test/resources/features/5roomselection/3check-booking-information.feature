Feature: Check information about reserved room
  Background:
    Given I am on hotel page
    And I choose some hotel room

  Scenario: Check information about reserved room for next booking step
    When I click on 'Submit reservation button'
    Then I am on booking details page with actual information about reservation
Feature: Sign-out

  Scenario: Check the sign out possibility

    Given I am logged in
    When I try to sign out
    Then I am not logged in anymore
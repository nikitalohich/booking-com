Feature: Registration

    Scenario: Open registration form
      Given I am logged off
      When I click to the registration button
      And I input user credentials and click submit button
      Then I am signed in
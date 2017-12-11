Feature: Sign-in
  Background:
    Given I am on 'main' page

  Scenario: Open Sign in form
    Given I am logged off
    When  I sign in as "Uladzimir"
    Then  I am signed in and notifications are visible
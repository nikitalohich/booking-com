Feature: Booking-details-fields
  Background:
    Given I am on 'Booking Details' page

    Scenario: Changing booking date to past
    When I clean all fields
    Then 3 and more fields are required to filling

    Scenario Outline: Checking email regex
      When I input "<emails>" into 'email' field
      Then 'warning message about wrong email' is visible
      Examples: Invalid emails
        |emails                       |
        |notvalidemail                |
        |maybenotvalid@test.test.test |

    Scenario: Filling guest name is available
      When I input "Vasily Sidorov" into 'guest name' field
      Then  'guest email field' is visible
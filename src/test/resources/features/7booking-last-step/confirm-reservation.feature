Feature: Confirm-Reservation

  Scenario: Set all valid dates
    When I input valid data
    And I submit the form
    Then Booking Confirmation page should be opened


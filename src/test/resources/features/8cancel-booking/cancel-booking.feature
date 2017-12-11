Feature: Cancel-Reservation

  Scenario: Cancel booking
    When I cancel the booking
    Then Message telling me the booking was canceled is visible


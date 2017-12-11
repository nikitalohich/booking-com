Feature: Booking with invalid data

  Scenario: Check the impossibility of room booking with an expired card

    Given I am on the confirm reservation page
    When I try to book a room with the card expired 02/2017
    Then Page should contain the expiration date notification
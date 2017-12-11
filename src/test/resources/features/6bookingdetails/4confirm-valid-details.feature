Feature: Submitting valid details

  Background:
    Given I am on 'Booking Details' page

  Scenario:  Submitting valid details
    When Submit valid details
    Then 'Confirm Reservation' page should be opened
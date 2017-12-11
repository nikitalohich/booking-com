Feature: Booking-details-dates
  Background:
  Given I am on 'Booking Details' page

   Scenario: Changing booking date to past
     When I click on 'changes dates' link
     And I change check-in date to past
     Then Changing date to past is not available

   Scenario: Comparing booking price in different month
     Given 'Changing dates pop-up' is visible
     When I change booking check-in from current month to next month
     And I click on 'change dates button'
     Then Price after changing date to next month is equals or less then current price
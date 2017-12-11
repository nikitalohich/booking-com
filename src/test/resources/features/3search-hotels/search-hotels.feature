Feature: Destination search

    Scenario: Destination search without dates
      Given I am on 'Main' page
      When I input 'destination' into 'Destination' field and I click on 'search button'
      Then 'HotelFilter' page should be opened and 'choose dates message' is visible

    Scenario: Destination search with dates
      Given I am on 'Hotel Filter' page
      When I input 'check-in' into 'Check-In' field and I click on 'search button'
      Then 'HotelFilter' page should be opened and valid date remain the same
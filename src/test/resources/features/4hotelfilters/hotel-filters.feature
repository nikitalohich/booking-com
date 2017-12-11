Feature: Hotel-filters
  Background:
    Given I am on 'Hotel Filter' page

  Scenario: Price hotel filtering
    When I mark 'Your budget' checkboxes lower than Trip budget
    Then Hotels from search list should be match to selected checkboxes

  Scenario: Star rating hotel filtering
    When I mark 'Star rating' checkboxes fit to Trip star rating
    Then Hotels from search list match to selected checkboxes
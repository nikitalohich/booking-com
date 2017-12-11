Feature: Warning message

    Scenario: Warning message must be presented
      Given I am on 'Booking Details' page
      When I clean all fields
      And I click on 'submit details button'
      Then 'warning message about wrong details' is visible
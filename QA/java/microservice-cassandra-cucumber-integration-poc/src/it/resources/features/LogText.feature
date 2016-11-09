Feature: Save a log text in Cassandra

  Scenario: Save a log text
    Given I store a log with ID 13 and text "TEST!"
    When I retrieve the log with ID 13
    Then I should see "TEST!" in the retrieved log text

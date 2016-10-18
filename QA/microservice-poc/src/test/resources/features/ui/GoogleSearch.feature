Feature: Test basic Google search engine functionality

  Scenario: Perform a search in Google
    Given User enters "Something" in "Search Box" field
    When User clicks the 3 link result
    Then Browser title should be "Something (Beatles song)"
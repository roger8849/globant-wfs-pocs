Feature: Retrieve sample contents from /samples endpoint of RESTful API

  Scenario: Retrieve all samples available
    Given I query the GET endpoint "/samples"
    When I retrieve the response
    Then I should see 5 samples in the response
    And Response should contain:
      | RUSO SAMPLE CONTENT                                |
      | SAMPLE CONTENT 2                                   |
      | HEY! I AM A SAMPLE CONTENT                         |
      | CONTENT...CONTENT EVERYWHERE...                    |
      | DID YOU JUST ASSUME I WAS A CONTENT??? *triggered* |

  Scenario: Retrieve all samples available
    Given I query the GET endpoint "/samples"
    When I retrieve the response
    Then I should see 5 samples in the response
    And Response should contain: RUSO SAMPLE CONTENT, SAMPLE CONTENT 2, HEY! I AM A SAMPLE CONTENT, CONTENT...CONTENT EVERYWHERE..., DID YOU JUST ASSUME I WAS A CONTENT??? *triggered*

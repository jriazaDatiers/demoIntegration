Feature: Add Article to existing list

  @TEST_SPIN-309 @rq3
  Scenario: Add Article to existing list
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I add an article to existing List
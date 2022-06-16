Feature: Delete Article Lists but fail due read only user

  @TEST_SPIN-314 @rq3
  Scenario: Delete Article Lists but fail due read only user
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I delete an article list but fail not owner
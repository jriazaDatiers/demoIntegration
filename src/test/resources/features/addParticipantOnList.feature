Feature: Add participant in my list

  @TEST_MSFDEM-91 @rq3
  Scenario: Add participant in my list
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I add a participant in my list


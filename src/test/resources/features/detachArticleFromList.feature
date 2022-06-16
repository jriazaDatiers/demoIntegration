Feature: Detach article to existing list

  @TEST_SPIN-289 @demo04 @detachArticleExistingList @rq3
  Scenario: Detach article to existing list
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I detach an article from existing List


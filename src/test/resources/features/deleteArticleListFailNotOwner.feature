Feature: Delete Article Lists but fail because not owner
@deleteArticleFailNotOwner
  Scenario: Delete Article Lists but fail because not owner

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I delete an article list but fail not owner
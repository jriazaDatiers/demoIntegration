Feature: Add article to existing list

  Scenario: Add article to existing list

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I add an article to existing List


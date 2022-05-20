Feature: createArticleListFromExcel
@createArticleListFromExcel
  Scenario: create article list from excel in article list

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I click on create list from Excel

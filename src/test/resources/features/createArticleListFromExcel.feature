Feature: createArticleListFromExcel

  Scenario Outline: create article list from excel in article list

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go Articles
    Then I go to Article Lists
    Then I click on create list from Excel <name>


    Examples:
      |url|user|password|name|
      | "https://staging.unidata.msf.org/" | "ESCDO" | "Downy2020!!" |"Escobar"|
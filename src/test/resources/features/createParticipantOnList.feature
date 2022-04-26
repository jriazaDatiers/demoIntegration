Feature: createArticleListFromExcel

  Scenario Outline: create article list from excel in article list

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go to Articles
    Then I go to Article Lists
    Then I add a participant in my list <name> <participant>



    Examples:
      |url|user|password|name|participant|
      | "https://staging.unidata.msf.org/" | "ESCDO" | "Downy2020!!" |"Escobar"|"Juan Manuel Moron Riaza"|
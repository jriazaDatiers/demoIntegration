Feature: Detach article to existing list

  Scenario Outline: Detach article to existing list

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go Articles
    Then I go to Article Lists
    Then I detach an article from existing List


    Examples:
      |url|user|password|name|
      | "https://vm20.unidata.msf.org/" | "ESCDO" | "Downy2020!!" |"Escobar"|
Feature: delete Article list

  Scenario Outline: Delete Article Lists

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go Articles
    Then I go to Article Lists
    Then I delete an article list but fail not owner <name> <hasToSucceed>


    Examples:
      |url|user|password|name|hasToSucceed |
      | "https://vm20.unidata.msf.org" | "ESCDO" | "Downy2020!!" |"Escobar"|"True"|
Feature: delete Article list

  Scenario Outline: Delete Article Lists

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go Articles
    Then I go to Article Lists
    Then I delete an article list


    Examples:
      |url|user|password|
      | "https://staging.unidata.msf.org/" | "ESCDO" | "Downy2020!!" |
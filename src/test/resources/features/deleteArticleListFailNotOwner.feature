Feature: Delete Article Lists but fail because not owner

  Scenario Outline: Delete Article Lists but fail because not owner

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go Articles
    Then I go to Article Lists
    Then I delete an article list but fail not owner <name> <hasToSucceed>


    Examples:
      | url                                | user    | password      | name     | hasToSucceed |
      | "https://staging.unidata.msf.org/" | "ESCDO" | "Downy2020!!" | "Conrad" | "False"     |
Feature: Participate in Article list management

  @article @Regression @ReadOnly
  Scenario Outline: Participate in Article list management

    When I go to <url>
    When I log in with user <user> and password <password>
    Then I go to Articles
    Then I go to Article Lists
    Then I find the lists where I am participant
    Then I add an article to existing List as Participant
    Then I delete an article list as Participant
    Then I Change the name of the Article list fail due rights




    Examples:
      |url|user|password|participant|
      | "https://staging.unidata.msf.org/" | "ESCDO" | "Downy2020!!" |"Escobar"|
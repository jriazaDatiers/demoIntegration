Feature: MSDDEM-57
@home
  Scenario Outline: read only users

    When I go to <url>
    Then I check the links in Unidata Home
    Then I log in with user <user> and password <password>


    Examples:
      |url|user|password|
      | "https://staging.unidata.msf.org/" | "ESCDO" | "Downy2020!!" |
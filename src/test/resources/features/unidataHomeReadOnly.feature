Feature: Unidata home page, read only, check links

  Scenario: read only users

    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password


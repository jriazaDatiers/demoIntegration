Feature: Unidata home page, read only, check links
@demo04
  Scenario: read only users

    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password


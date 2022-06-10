Feature: Link Check

 @linkCheck @AllProfiles
  Scenario: Link Check
    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password
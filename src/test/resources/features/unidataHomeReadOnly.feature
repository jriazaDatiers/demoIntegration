Feature: Unidata home page, read only, check links

  @TEST_SPIN-300 @demo04
  Scenario: Unidata home page, read only, check links
    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password



Feature: Link Check

  @TEST_SPIN-261 @linkCheck
  Scenario: Link Check
    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password
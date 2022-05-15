Feature: Link Check staging

  @TEST_SPIN-163 @TEST_SPIN-163 @staging @linkCheck
  Scenario: Link Check staging
    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password

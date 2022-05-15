Feature: Link Check staging

	#Test for Link Check in staging environment
  @TEST_SPIN-261 @linkCheck
  Scenario: Link Check staging
    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password
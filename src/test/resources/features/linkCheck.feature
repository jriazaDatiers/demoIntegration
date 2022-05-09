Feature: Link Check staging

  @TEST_SPIN-163 @staging
  Scenario: Link Check staging
    When I go to "https://staging.unidata.msf.org/"
    Then I check the links in Unidata Home
    Then I log in with user "ESCDO" and password "Downy2020!!"
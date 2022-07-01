Feature: Duplicate an STD article and make a NST article

  @TEST_SPIN-92 @UD_dataSteward @rq3
  Scenario: Duplicate an STD article and make a NST article
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I select a STD article and the duplicate functionality
    Then I add a picture to the Article
    Then I go to the Main tab
    Then I update the specification
    Then I update the Label English
    Then I update the lyfecycle to Valid
    Then I verify the manufacturer is empty
    Then I update the standardization to NST
    Then I save the record
    Then I check the article has status unmatched
    Then I close the article
    Then I check if the record was created

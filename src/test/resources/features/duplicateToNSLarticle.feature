Feature: Duplicate an STD/NST/NSL article and make a NSL article

  @TEST_SPIN-350 @UD_DataOwner @UD_dataSteward @rq3
  Scenario: Duplicate an STD/NST/NSL article and make a NSL article
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I select a NST article and the duplicate functionality
    Then I add a picture to the Article
    Then I go to the Main tab
    Then I update the specification
    Then I update the Label English
    Then I update the lyfecycle to Valid
    Then I verify the manufacturer is empty
    Then I update the standardization to NSL
    Then I save the record
    Then I check the article has status unmatched
    Then I close the article


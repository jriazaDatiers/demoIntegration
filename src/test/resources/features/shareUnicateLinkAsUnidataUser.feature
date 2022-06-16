Feature: Following unicat links as a UniData user

  @TEST_SPIN-15 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @rq3
  Scenario: Following unicat links as a UniData user
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I click on the sharing link on specific article
    Then I validate the content of the product in Unicat
Feature: Sharing unicat links from UniData

  @TEST_SPIN-138 @UD_AllProfiles @UD_DataOwner @UD_DataSteward @UD_ReadOnly @UD_Referent @shareUnicatLink @unicat_MSF
  Scenario: Sharing unicat links from UniData
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I click on specific article
    Then I validate the content of the product in Unicat

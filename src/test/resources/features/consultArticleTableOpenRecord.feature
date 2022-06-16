Feature: Consult Article Table and Open a Record

  @TEST_SPIN-127 @UD_AllProfiles @UD_DataOwner @UD_DataSteward @UD_ReadOnly @UD_Referent @rq3
  Scenario: Consult Article Table and Open a Record
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I click on specific article
    Then I go to Articles
Feature: export "code-label-image" to excel

  @TEST_SPIN-133 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @rq3
  Scenario: export "code-label-image" to excel
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I select the Biomed Articles view
    Then I export a number of articles with the service code-label-image
    Then I check if the results are sorted alphabetically on code
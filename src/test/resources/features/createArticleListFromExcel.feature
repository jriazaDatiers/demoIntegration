Feature: Create an article list in UniData, by uploading an excel article list

  @TEST_SPIN-132 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @rq3
  Scenario: Create an article list in UniData, by uploading an excel article list
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I click on create list from Excel

Feature: Participate in Article list management

  @TEST_SPIN-150 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging
  Scenario: Participate in Article list management

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I find the lists where I am participant
    Then I add an article to existing List as Participant
    Then I delete an article list as Participant
    Then I Change the name of the Article list fail due rights
    Then I Delete the article list fail due rights


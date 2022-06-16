Feature: Consult a kit composition
@TEST_SPIN-130 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @consultKitComposition @rq3

  Scenario: Consult a kit composition

    When I go to url
    When I log in with user and password
    Then I go to Kit Compositions
    Then I click on TreeView
    Then I click on a Tree View branch
    Then I collect Article information
    Then I go to Composition Lists
    Then I click on a Kit Article
    Then I click on Kit Article tabs

Feature: Create a new NST Description
  @TEST_SPIN-32 @UD_DataOwner @UD_DataSteward @UD_Referent @UD_Warrantor @UD_staging
  Scenario: Create a new NST Description
    When I go to url
    When I log in with user and password
    Then I go to Descriptions Menu
    Then I click on Add Description button
    Then I change type to FFF
    Then I change family to CWAT
    Then I upload definition image
    Then I add Label ENG
    Then I add Label FR
    Then I save the description
    Then I confirm there is an identifier



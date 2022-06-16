Feature: Consult description
  @TEST_SPIN-129 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @rq3
  Scenario: Consult description

    When I go to url
    When I log in with user and password
    Then I go to Descriptions Menu
    Then I click on a Description
    Then I close the record
    Then I validate I am on the description table again


Feature: I check the homepage hyperlinks, then access through login and password


  @TEST_SPIN-135 @demo04 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_Live @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @linkCheck
  Scenario: I check the homepage hyperlinks, then access through login and password
    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password
Feature: I check the homepage hyperlinks, then access through login and password

  @TEST_UDT-14 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_Live @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @demo04 @linkCheck @rq3
  Scenario: I check the homepage hyperlinks, then access through login and password
    When I go to url
    Then I check the links in Unidata Home
    Then I log in with user and password

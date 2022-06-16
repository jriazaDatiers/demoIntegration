Feature: Change elements of UniData account

	#Change elements of UniData & Me
  @TEST_SPIN-126 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @changeElements @rq3
  Scenario: Change elements of UniData account
    When I go to url
    When I log in with user and password
    Then I go to My Unidata and inside
    Then I update the email with newEmail
    Then I check the email newEmail
    Then I update the email with oldEmail
    Then I check the email oldEmail



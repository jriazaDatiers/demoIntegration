Feature: Respond to Feedback

  @TEST_SPIN-45 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @rq3
  Scenario: Respond to Feedback
    When I go to url
    When I log in with user and password
    Then I go to Feedback Addressed to me
    Then I select a feedback addressed to me
    Then I type a new message Feedback
    Then I change the priority
    Then I add and addressee
    Then I save and close the feedback
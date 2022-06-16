Feature: Create a Feedback from an article record

  @TEST_SPIN-131 @UD_AllProfiles @UD_DataOwner @UD_DataServices @UD_DataSteward @UD_ReadOnly @UD_Referent @UD_Warrantor @UD_staging @rq3
  Scenario: Create a Feedback from an article record
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I create a new feedback addressing to addressee
    Then I type a new message Feedback
    Then I change the priority
    Then I add and addressee
    Then I save and close the feedback
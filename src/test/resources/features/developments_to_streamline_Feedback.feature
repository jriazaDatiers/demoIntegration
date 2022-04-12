@REQ_MSFDEM-50
Feature: developments to streamline Feedback

	#log in
	#go to the menu item “Feedback addressed to me”
	#Open the feedback at the top
	#Add an answering message
	#Add a new addressee in the Feedback
	#Save and close
  @TEST_MSFDEM-67 @REQ_MSFDEM-51 @REQ_MSFDEM-51 @UD_ReadOnly
  Scenario: Respond to a Feedback message addressed to you (Cucumber)
    When I log in
    Then I go to the menu item Feedback addressed to me
    Then I Open the feedback at the top
    Then I Add an answering message
    Then I Add a new addressee in the Feedback
    Then I Save and close

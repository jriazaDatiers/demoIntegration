@REQ_MSFDEM-50
Feature: developments to streamline Feedback Initiated by me

	#log in
	#go to the menu item Feedback Initiated by me
	#Open the feedback at the top
	#Add an answering message
	#Set status of the Feedback to Closed
	#Save and close
  @TEST_MSFDEM-68 @REQ_MSFDEM-51 @REQ_MSFDEM-51 @UD_ReadOnly
  Scenario: Close a Feedback message created by you (Cucumber)
    When I log in
    Then I go to the menu item Feedback initiated by me
    Then I Open the feedback at the top
    Then I Add an answering message
    Then I Set the status of the Feedback message to Closed
    Then I Save and close

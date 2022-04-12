@REQ_MSFDEM-50
Feature: Create a Feedback on an Article

	#user logs in
	#goes to an article record
	#Goes to the Feedback tab
	#creates a Feedback with the + button
	#addresses to a user
	#adds a Message
	#Sets a priority
	#Saves
  @TEST_MSFDEM-69 @REQ_MSFDEM-51 @REQ_MSFDEM-51 @UD_AllProfiles @readOnly
  Scenario: Create a Feedback on an Article (Cucumber)
    When I log in
    Then I go to an article record
    Then I go to the Feedback tab
    Then I create a Feedback with the + button
    Then I address to a user
    Then I add a Message
    Then I set a priority
    Then I Save and close
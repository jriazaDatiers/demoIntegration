Feature: Respond Feedback

  Scenario: Respond Feedback

    When I go to url
    When I log in with user and password
    Then I go to Feedback Addressed to me
    Then I type a new message Feedback
    Then I add and addressee
    Then I change the priority
    Then I save and close the feedback
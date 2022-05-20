Feature: Create feedback
@CreateFeedback
  Scenario: Create feedback

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I create a new feedback addressing to addressee

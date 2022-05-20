Feature: createParticipantOnList
@createParticipantOnList
  Scenario: createParticipantOnList

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I go to Article Lists
    Then I add a participant in my list

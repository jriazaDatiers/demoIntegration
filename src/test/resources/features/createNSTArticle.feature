Feature: Data Owner: Create a NST article

  Scenario: Data Owner: Create a NST article

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I click on an Article
    Then I add group to Article
    Then I add Family to Article
    Then I add Root to Article
    Then I add Specification to Article
    Then I update the standardization to NST
    Then I add Article Label ENG
    Then I add Article Label FR
    Then I change lyfeCycle Article to valid
    Then I save the Article
    Then I confirm there is an identifier
    Then I check if there are errors on screen
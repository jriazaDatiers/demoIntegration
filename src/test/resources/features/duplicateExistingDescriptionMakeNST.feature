Feature: Duplicate an existing Description and make a NST Description

  Scenario: Duplicate an existing Description and make a NST Description

    When I go to url
    When I log in with user and password
    Then I go to Descriptions Menu
    Then I select an specific Description
    Then I perform the duplicate action
    Then I update the standardization to NST
    Then I update the Article code
    Then I update the Label English
    Then I update the Label French
    Then I upload definition image
    Then I save the description
    Then I confirm there is an identifier
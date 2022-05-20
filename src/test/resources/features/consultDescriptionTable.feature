Feature: Consult description

  Scenario: Consult description

    When I go to url
    When I log in with user and password
    Then I go to Descriptions Menu
    Then I click on a Description
    Then I close the record
    Then I validate I am on the description table again


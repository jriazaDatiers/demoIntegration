Feature: Export a kit composition list to excel, in 2 layouts: grouped and simple

  Scenario: Export in 2 layouts

    When I go to url
    When I log in with user and password
    Then I go to Kit Compositions
    Then I go to Composition Lists
    Then I select kit articles
    Then I export them grouped
    Then I download the excel
    Then I check the excel

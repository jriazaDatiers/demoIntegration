Feature: Export code label image to Excel

  Scenario: Export code label image to Excel

    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I select the Biomed Articles view
    Then I export a number of articles with the service code-label-image
    Then I check if the results are sorted alphabetically on code

Feature: Duplicate an STD/NST/NSL article

  Scenario: Duplicate an STD/NST/NSL article
    When I go to url
    When I log in with user and password
    Then I go to Articles
    Then I select an article and the duplicate functionality
    Then I modify the new record
    Then I check if the record was created